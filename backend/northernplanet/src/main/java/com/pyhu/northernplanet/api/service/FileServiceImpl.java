package com.pyhu.northernplanet.api.service;

import com.pyhu.northernplanet.api.request.FileReq;
import com.pyhu.northernplanet.api.request.ShowFileReq;
import com.pyhu.northernplanet.api.response.FileRes;
import com.pyhu.northernplanet.api.response.FiledetailRes;
import com.pyhu.northernplanet.db.entity.Presentations;
import com.pyhu.northernplanet.db.entity.RoomsPresentations;
import com.pyhu.northernplanet.db.repository.FileGroupRepository;
import com.pyhu.northernplanet.db.repository.FileRepository;
import com.pyhu.northernplanet.db.repository.RoomRepository;
import com.pyhu.northernplanet.db.repository.UserRepository;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileServiceImpl implements FileService {
	@Autowired
	FileRepository fileRepository;
	@Autowired
	FileGroupRepository filegroupRepository;
	@Autowired
	RoomRepository roomRepository;

	@Autowired
	UserRepository userRepository;

	@Autowired
	FileGroupRepository groupRepository;

	private final Logger log = LoggerFactory.getLogger(FileServiceImpl.class);

	@Override
	public FileReq saveFile(FileReq filereq) throws Exception {
		RoomsPresentations pr = new RoomsPresentations();
		if (pr.getUpload_time() == null) {
			Timestamp timestamp = new Timestamp(System.currentTimeMillis());
			pr.setUpload_time(timestamp);
		}
		pr.setRooms(roomRepository.findByRoomId(filereq.getRoom_id()));
		pr.setUsers(userRepository.findByUserId(filereq.getUser_id()));
		pr.setFileSize(filereq.getFiles().size());
		groupRepository.save(pr);
		int cnt = 0;
		List<String> pat = FileHandler.parseFileInfo(filereq);
		for (MultipartFile multipartFile : filereq.getFiles()) {
			Presentations fil = new Presentations();
			fil.setDirectory(pat.get(cnt));
			fil.setName(Integer.toString(cnt + 1));
			fil.setOriginal(multipartFile.getOriginalFilename());
			fil.setRoomspresentations(pr);
			cnt++;
			fileRepository.save(fil);
		}
		return filereq;
	}

	@Override
	public List<FileRes> findbyroomid(int roomId) {
		List<RoomsPresentations> rp = filegroupRepository.findByrooms_RoomId(roomId);
		List<FileRes> fr = new ArrayList<FileRes>();
		for (int i = 0; i < rp.size(); i++) {
			RoomsPresentations room = rp.get(i);
			FileRes file = new FileRes(room.getUsers().getUserId(), room.getFileSize());
			fr.add(file);

		}
		return fr;
	}

	@Override
	public List<FiledetailRes> findbygroupid(ShowFileReq filereq) {
		log.info("[findByGroupId] Room {} and User {}", filereq.getRoom_id(), filereq.getUser_id());
		List<FiledetailRes> res = new ArrayList<FiledetailRes>();
		try {
			Integer group = filegroupRepository.findBygroupid(filereq.getRoom_id(), filereq.getUser_id());
			if (group != null) {
				List<Presentations> list = fileRepository.findByroomspresentations_GroupId(group);

				for (int i = 0; i < list.size(); i++) {
					Presentations p = list.get(i);
					FiledetailRes fr = new FiledetailRes(p.getDirectory(), p.getOriginal());
					res.add(fr);
				}
			} else {
				return null;
			}

		} catch (Exception e) {
			log.error("[findbygroupid] error:{}", e);
			e.printStackTrace();
		}
		return res;
	}

	@Override
	public void deletefile(FileReq registerInfo) {
		int group = filegroupRepository.findBygroupid(registerInfo.getRoom_id(), registerInfo.getUser_id());
		RoomsPresentations pr = filegroupRepository.getById(group);
		filegroupRepository.delete(pr);
	}

	@Override
	public FileReq update(FileReq registerInfo) throws Exception {
		deletefile(registerInfo);
		return saveFile(registerInfo);
	}

	@Override
	public void delete(ShowFileReq registerInfo) {
		int group = filegroupRepository.findBygroupid(registerInfo.getRoom_id(), registerInfo.getUser_id());
		RoomsPresentations pr = filegroupRepository.getById(group);

		filegroupRepository.delete(pr);

	}

	@Override
	public byte[] getImage(int roomId, int userId, int currentPage) throws IOException {
		InputStream imageStream = new FileInputStream(
				"/home/ubuntu/presentations/" + roomId + "/" + userId + "/" + currentPage + ".jpg");
//		InputStream imageStream = new FileInputStream(
//				"C:\\Users\\multicampus\\presentations\\" + roomId + "\\" + userId + "\\" + currentPage + ".jpg");
		byte[] imageByteArray = IOUtils.toByteArray(imageStream);
		imageStream.close();
		return imageByteArray;
	}

	@Override
	public List<String> getAllImages(int roomId, int userId) throws IOException {
		List<String> imageStringList = new ArrayList<String>();
		int size = filegroupRepository.getFileSizeByGroupId(roomId, userId);
		for (int i = 1; i <= size; i++) {
//			InputStream imageStream = new FileInputStream(
//					"C:\\presentations\\" + roomId + "\\" + userId + "\\" + i + ".jpg");
			InputStream imageStream = new FileInputStream(
					"/home/ubuntu/presentations/" + roomId + "/" + userId + "/" + i + ".jpg");
			byte[] imageByteArray = IOUtils.toByteArray(imageStream);
			imageStream.close();
			String encodedString = Base64.getEncoder().encodeToString(imageByteArray);
			imageStringList.add(encodedString);
		}
		return imageStringList;
	}

}