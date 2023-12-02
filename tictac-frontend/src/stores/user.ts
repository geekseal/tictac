import { ref } from 'vue';
import { defineStore } from 'pinia';
import axios from 'axios';

const REST_USER_API = `http://localhost:8080`;

function b64DecodeUnicode(str: string) {
  return decodeURIComponent(
    Array.prototype.map
      .call(atob(str), function (c) {
        return '%' + ('00' + c.charCodeAt(0).toString(16)).slice(-2);
      })
      .join('')
  );
}

export interface User {
  userId: string;
  password?: string;
  username: string;
  email?: string;
}

export const useUserStore = defineStore('user', () => {
  const loginUserId = ref('');
  const loginUsername = ref('');
  const loginEmail = ref('');

  // https://stackoverflow.com/questions/55700815/async-await-with-vuex-dispatch
  function login(id: string, pw: string) {
    return axios
      .post(`${REST_USER_API}/login`, {
        userId: id,
        password: pw
      })
      .then((response: any) => {
        sessionStorage.setItem('access-token', response.data['access-token']);

        const token = response.data['access-token'].split('.');
        let loginInfo = token[1]; // 3개 중에 payload 고름
        loginInfo = b64DecodeUnicode(loginInfo);
        console.log(loginInfo);
        loginInfo = JSON.parse(loginInfo);

        loginUserId.value = loginInfo['userId'];
        loginUsername.value = loginInfo['username'];
        loginEmail.value = loginInfo['email'];

        sessionStorage.setItem('userId', loginInfo['userId']);
        sessionStorage.setItem('username', loginInfo['username']);
        sessionStorage.setItem('email', loginInfo['email']);
      })
      .catch((err: Error) => {
        console.log(err);
      });
  }

  const logout = () => {
    sessionStorage.removeItem('access-token');
    loginUserId.value = '';
    loginUsername.value = '';
    loginEmail.value = '';
  };

  const username = ref('');

  const getUsername = function (userId: string) {
    return axios.get(`${REST_USER_API}/@${userId}`).then((response: any) => {
      username.value = response.data;
    });
  };

  return { login, logout, loginUserId, loginUsername, loginEmail, getUsername, username };
});
