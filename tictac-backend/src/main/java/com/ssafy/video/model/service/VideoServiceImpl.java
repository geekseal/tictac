package com.ssafy.video.model.service;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;

import javax.imageio.ImageIO;

import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.Java2DFrameConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.ssafy.video.model.dao.VideoDao;
import com.ssafy.video.model.dto.Video;

@Service
public class VideoServiceImpl implements VideoService {

    private String uploadPath = new FileSystemResource("src/main/resources/static/upload").getFile().getAbsolutePath();
	
	private VideoDao videoDao;
	
	@Autowired
	ResourceLoader resLoader;
	private static final Logger logger = LoggerFactory.getLogger(VideoServiceImpl.class);
	
	@Autowired
	public void setVideoDao(VideoDao videoDao) {
		this.videoDao = videoDao;
	}
	
	
	@Override
	public List<Video> getWholeList() {
		return videoDao.selectAllVideo();
	}

	@Override
	public List<Video> getListBySearchWord(String searchWord) {
		return videoDao.selectBySearchWord(searchWord);
	}

	@Override
	public List<Video> getListByCategoryId(String categoryId) {
		return videoDao.selectByCategoryId(categoryId);
	}

	@Override
	public List<Video> getUploadList(String userId) {
		return videoDao.selectUploadList(userId);
	}

	@Override
	public List<Video> getMyBookmarkList(String userId) {
		return videoDao.selectMyBookmarkList(userId);
	}

	
	
	@Override
	public Video getVideoDetail(int videoId) {
		videoDao.updateViewCnt(videoId);
		return videoDao.selectOneVideo(videoId);
	}

	@Override
	public int uploadVideo(Video video, MultipartFile file, MultipartFile thumbnail) {
		try {
			fileHandling(video, file, thumbnail);
		} catch (IOException e) {
			e.printStackTrace();
			//위에서 실패할 시 0을 리턴하면 될 것인가?
			return 0;
		}
		return videoDao.insertVideo(video);
	}

	@Override
	public int modifyVideo(Video video) {
		return videoDao.updateVideo(video);
	}

	@Override
	public int removeVideo(int videoId) {
		return videoDao.deleteVideo(videoId);
	}
	
	private void fileHandling(Video video, MultipartFile file, MultipartFile thumbnail) throws IOException {
		//폴더 생성
		File uploadPathFolder = new File(uploadPath);
        if(uploadPathFolder.exists() == false) {
	        uploadPathFolder.mkdirs();
        }

		if (file != null && file.getSize()>0) {
			
			//UUID
	        String uuid = UUID.randomUUID().toString();
	        //비디오 객체 수정
			video.setVideoSrc(uuid + "_" + file.getOriginalFilename());
			video.setOrgVideoSrc(file.getOriginalFilename());
			int idx = video.getOrgVideoSrc().lastIndexOf(".");
			video.setThumbnailImgSrc(uuid + "_" + video.getOrgVideoSrc().substring(0, idx) + ".png");
			
	        //저장할 파일 이름 중간에 "_"를 이용하여 구분
	        String saveVideoName = uploadPath + File.separator +  video.getVideoSrc();
	        String saveThumbnailName = uploadPath + File.separator + video.getThumbnailImgSrc();
	        //Paths.get() 메서드는 특정 경로의 파일 정보를 가져옵니다.(경로 정의하기)
	        Path saveVideoPath = Paths.get(saveVideoName);
	        Path saveThumbnailPath = Paths.get(saveThumbnailName);
	        
			try {
	        	file.transferTo(saveVideoPath);
	            thumbnail.transferTo(saveThumbnailPath);
	        } catch (IOException e) {
	             e.printStackTrace();
	        }
		}
	}

}
