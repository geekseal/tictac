package com.ssafy.video.controller;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.ssafy.video.model.dto.Video;
import com.ssafy.video.model.service.CommentService;
import com.ssafy.video.model.service.VideoService;

import io.swagger.annotations.ApiParam;

@RestController
@CrossOrigin
public class VideoController {
	
	private String uploadPath = new FileSystemResource("src/main/resources/static/upload").getFile().getAbsolutePath();
	
	@Autowired
	ResourceLoader resLoader;
	
	@Autowired
	VideoService videoService;

	// 1. 영상 리스트
	
	// 전체 비디오 리스트 가져오기
	@GetMapping("/videoList")
	private ResponseEntity<?> wholeList() {
		List<Video> list = videoService.getWholeList();
		return new ResponseEntity<List<Video>>(list, HttpStatus.OK);
	}

	// 검색해서 비디오 리스트 가져오기
	// RequestParam도 같이써야하나
	@GetMapping("/videoList/search")
		private ResponseEntity<?> searchList(@ApiParam(
			    name =  "searchWord",
			    type = "String",
			    value = "search condition",
			    example = "skate board",
			    required = true) String searchWord) {
			List<Video> list = videoService.getListBySearchWord(searchWord);
			if (list == null || list.size() == 0) {
				return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
			}
			return new ResponseEntity<List<Video>>(list, HttpStatus.OK);
		}

	// 카테고리별 영상 리스트 가져오기
	@GetMapping("/videoList/{categoryId}")
	private ResponseEntity<?> categoryList(@PathVariable String categoryId) {
		List<Video> list = videoService.getListByCategoryId(categoryId);
		if (list == null || list.size() == 0) {
			return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<Video>>(list, HttpStatus.OK);
	}

	// 해당 유저의 업로드리스트
	@GetMapping("uploadList/{userId}")
	private ResponseEntity<?> uploadList(@PathVariable String userId) {
		List<Video> list = videoService.getUploadList(userId);
		if (list == null || list.size() == 0) {
			return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<Video>>(list, HttpStatus.OK);
	}

	// 로그인된 사용자가 자기꺼만 볼수있어야함
	// 해당 유저의 찜리스트
	@GetMapping("myBookmarkList/{userId}")
	private ResponseEntity<?> myBookmarkList(@PathVariable String userId) {
		List<Video> list = videoService.getMyBookmarkList(userId);
		if (list == null || list.size() == 0) {
			return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<Video>>(list, HttpStatus.OK);
	}
	
	
	// 2. 영상 1개
	
	// videoId 원래 int인거 고려안해도 되나? String으로 받아야하나?
	// 영상 상세 조회
	@GetMapping("/video/{videoId}")
	private ResponseEntity<Video> detail(@PathVariable Integer videoId) {
		Video video = videoService.getVideoDetail(videoId);
		return new ResponseEntity<Video>(video, HttpStatus.OK);
	}
	
	// 영상 업로드
	@PostMapping("/video")
	private ResponseEntity<Video> upload(Video video, @RequestPart List<MultipartFile> file) {
		if (file.size()>=2) {
			videoService.uploadVideo(video, file.get(0), file.get(1));
		}
		return new ResponseEntity<Video>(video, HttpStatus.OK);
	}
	
	// 영상 수정
	@PutMapping("/video")
	private ResponseEntity<Void> update(@RequestBody Video video) {
		videoService.modifyVideo(video);
		return new ResponseEntity<Void>(HttpStatus.OK);
	}
	
	// 영상 삭제
	@DeleteMapping("/video")
	private ResponseEntity<Void> delete(Integer videoId) {
		videoService.removeVideo(videoId);
		return new ResponseEntity<Void>(HttpStatus.OK);
	}
	
	// 3. video stream
	@GetMapping(path = "/stream/{videoSrc}", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
	public Resource video(@PathVariable String videoSrc) throws FileNotFoundException, IOException {
	    return new ByteArrayResource(FileCopyUtils.copyToByteArray(new FileInputStream(uploadPath+"/"+videoSrc)));
	}
	
	// 4. thumbnail
	@GetMapping(value = "/thumbnail/{thumbnailImgSrc}", produces = MediaType.IMAGE_JPEG_VALUE)
	public Resource getImageWithMediaType(@PathVariable String thumbnailImgSrc) throws IOException {
	    return new ByteArrayResource(FileCopyUtils.copyToByteArray(new FileInputStream(uploadPath+"/"+thumbnailImgSrc)));
	}
}
