
package com.pyhu.northernplanet.api.service;

import com.pyhu.northernplanet.api.request.FileReq;
import com.pyhu.northernplanet.api.request.ShowFileReq;
import com.pyhu.northernplanet.api.response.FileRes;
import com.pyhu.northernplanet.api.response.FiledetailRes;
import java.io.IOException;
import java.util.List;

public interface FileService {

  FileReq saveFile(FileReq filereq) throws Exception;

  // Rooms_Presentations getFile(int id);
  List<FileRes> findbyroomid(int roomId);

  List<FiledetailRes> findbygroupid(ShowFileReq filereq);

  byte[] getImage(int roomId, int userId, int currentPage) throws IOException;

  List<String> getAllImages(int roomId, int userId) throws IOException;

  FileReq update(FileReq registerInfo) throws Exception;

  void deletefile(FileReq registerInfo);

  void delete(ShowFileReq registerInfo);
}
