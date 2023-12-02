SELECT * FROM user;
SELECT * FROM video;
SELECT * FROM mybookmarklist;
SELECT * FROM comment;

-- user 테이블에 데이터 써줌
INSERT INTO user (userId, password, username, email)
VALUES ("ssafy", "1234", "김싸피", "ssafy@ssafy.com"),
("ssafy2", "5678", "김싸피2", "ssafy2@ssafy.com");

-- video 테이블에 데이터 써줌
INSERT INTO video (User_userId, content, videoSrc, regDate, thumbnailImgSrc, categoryId)
VALUES
("ssafy", "파쿠르1 영상입니다", "06958c6a-4583-414c-a920-f9de78126501_파쿠르1.mp4", STR_TO_DATE('22-11-2023','%d-%m-%Y'), "06958c6a-4583-414c-a920-f9de78126501_파쿠르1.png", "파쿠르"),
("ssafy", "스케이트보드1 영상입니다", "2a91c654-c5c7-4bb9-9cc1-19ad3ca0617b_스케이트보드1.mp4", STR_TO_DATE('22-11-2023','%d-%m-%Y'), "2a91c654-c5c7-4bb9-9cc1-19ad3ca0617b_스케이트보드1.png", "스케이트보드"),
("ssafy", "카포에라1 영상입니다", "6a71d240-ffda-4d76-aa07-8a19e5ee8669_카포에라1.mp4", STR_TO_DATE('22-11-2023','%d-%m-%Y'), "6a71d240-ffda-4d76-aa07-8a19e5ee8669_카포에라1.png", "카포에라"),
("ssafy", "광선검1 영상입니다", "b248b238-bd13-4dcf-8c65-44a49824bc81_광선검1.mp4", STR_TO_DATE('22-11-2023','%d-%m-%Y'), "b248b238-bd13-4dcf-8c65-44a49824bc81_광선검1.png", "광선검"),
("ssafy", "독 어질리티1 영상입니다", "a49d2ba3-d348-4725-8827-d59515d110d2_독어질리티1.mp4", STR_TO_DATE('22-11-2023','%d-%m-%Y'), "a49d2ba3-d348-4725-8827-d59515d110d2_독어질리티1.png", "독 어질리티"),
("ssafy", "파쿠르2 영상입니다", "c31ccf53-6cc7-44ef-a3fe-7b86ac3ec2c1_파쿠르2.mp4", STR_TO_DATE('22-11-2023','%d-%m-%Y'), "c31ccf53-6cc7-44ef-a3fe-7b86ac3ec2c1_파쿠르2.png", "파쿠르"),
("ssafy", "파쿠르3 영상입니다", "c4545009-cce0-4da6-a006-e9641eb2fcc8_파쿠르3.mp4", STR_TO_DATE('22-11-2023','%d-%m-%Y'), "c4545009-cce0-4da6-a006-e9641eb2fcc8_파쿠르3.png", "파쿠르"),
("ssafy", "카포에라2 영상입니다", "d7f2f044-7c1d-4945-865e-a3c45653d734_카포에라2.mp4", STR_TO_DATE('22-11-2023','%d-%m-%Y'), "d7f2f044-7c1d-4945-865e-a3c45653d734_카포에라2.png", "카포에라"),
("ssafy", "카포에라3 영상입니다", "90dea053-c7d5-4dd0-bc58-30acf98f382f_카포에라3.mp4", STR_TO_DATE('22-11-2023','%d-%m-%Y'), "90dea053-c7d5-4dd0-bc58-30acf98f382f_카포에라3.png", "카포에라");

INSERT INTO mybookmarklist (User_userId, Video_videoId)
VALUES ("ssafy", 10000),
("ssafy", 10001);

INSERT INTO comment (Video_videoId, commentId, User_userId, User_username, content, regDate)
VALUES (10000, 1, "ssafy", "김싸피", "댓글입니다", STR_TO_DATE('15-11-2023','%d-%m-%Y'));