import { ref } from 'vue';
import { defineStore } from 'pinia';
import axios from 'axios';

export interface Comment {
  commentId?: number;
  userId: string;
  username: string;
  content: string;
  regDate?: Date;
}

const REST_COMMENT_API = `http://localhost:8080`;

export const useCommentStore = defineStore('comment', () => {
  const commentList = ref([]);
  function getCommentList(videoId: any) {
    return axios.get(REST_COMMENT_API + `/video/${videoId}/comment`).then((response: any) => {
      commentList.value = response.data;
      // console.log(response.data);
    });
  }

  const uploadComment = function (videoId: number, comment: Comment) {
    return axios({
      url: REST_COMMENT_API + `/video/${videoId}/comment`,
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
        // 아래줄 - spring interceptor가 토큰 유효성 검사하기 위해 넣음
        'access-token': sessionStorage.getItem('access-token')
      },
      data: {
        userId: comment.userId,
        username: comment.username,
        content: comment.content
      }
    })
      .then((res) => {
        console.log(res);
      })
      .catch((err) => {
        console.log(err);
      });
  };

  const deleteComment = function (videoId: number, commentId: any) {
    return axios.delete(REST_COMMENT_API + `/video/${videoId}/comment`, {
      params: {
        commentId: commentId
      }
    });
  };

  return { commentList, getCommentList, uploadComment, deleteComment };
});
