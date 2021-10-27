package com.pyhu.northernplanet.api.controller;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pyhu.northernplanet.api.request.RoomPostReq;
import com.pyhu.northernplanet.api.response.RoomGetRes;
import com.pyhu.northernplanet.api.service.ParticipantService;
import com.pyhu.northernplanet.api.service.RoomService;
import com.pyhu.northernplanet.api.service.UserService;
import com.pyhu.northernplanet.common.response.ApiResponseDto;
import com.pyhu.northernplanet.db.entity.Rooms;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@Slf4j
@Api(value = "방 관련 API", tags = {"Room"})
@RestController
@RequiredArgsConstructor
@RequestMapping("/rooms")
public class RoomController {

  private final RoomService roomService;
  private final UserService userService;
  private final ParticipantService participantService;


  @PostMapping("/create")
  @ApiOperation(value = "방 생성")
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm A", timezone = "Asia/Seoul")
  @ApiResponses({@ApiResponse(code = 200, message = "성공"),
      @ApiResponse(code = 401, message = "인증 실패"),
      @ApiResponse(code = 404, message = "사용자 없음"), @ApiResponse(code = 500, message = "서버 오류")})
  public ApiResponseDto<Rooms> register(
      @RequestBody @ApiParam(value = "방정보", required = true) RoomPostReq registerInfo) {
    Rooms room=null;
    try {
      log.info("[register] room register info: {}", registerInfo);
      room = roomService.createRoom(registerInfo);
      log.info("[register] room : {}", room);
      return ApiResponseDto.success(room);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return ApiResponseDto.fail(room, "방 생성 실패했습니다.");
  }
//
//  @PutMapping("/update/{roomId}")
//  @ApiOperation(value = "방 정보 수정")
//  @ApiResponses({@ApiResponse(code = 200, message = "성공"),
//      @ApiResponse(code = 401, message = "인증 실패"),
//      @ApiResponse(code = 404, message = "사용자 없음"), @ApiResponse(code = 500, message = "서버 오류")})
//  public ResponseEntity<? extends BaseResponseBody> update(
//      @RequestBody @ApiParam(value = "방업데이트", required = true) RoomUpdateReq roomInfo) {
//    try {
//      log.info("[update] roomUpdateReq: {}", roomInfo);
//      Rooms room = roomService.updateRoom(roomInfo);
//      log.info("[update] room: {}", room);
//      return ResponseEntity.status(200).body(BaseResponseBody.of(200, "Success"));
//    } catch (Exception e) {
//      e.printStackTrace();
//    }
//    return ResponseEntity.status(500).body(BaseResponseBody.of(500, "fail"));
//  }
//
//  @DeleteMapping("/delete/{roomId}")
//  @ApiOperation(value = "방 삭제")
//  @ApiResponses({@ApiResponse(code = 200, message = "성공"),
//      @ApiResponse(code = 401, message = "인증 실패"),
//      @ApiResponse(code = 404, message = "사용자 없음"), @ApiResponse(code = 500, message = "서버 오류")})
//  public ResponseEntity<? extends BaseResponseBody> deleteRoom(@PathVariable("roomId") int roomId) {
//    log.info("[DeleteRoom] room {} is deleted - controller", roomId);
//    Rooms room = roomService.getRoom(roomId);
//    roomService.deleteRoom(roomId);
//
//    if (room == null) {
//      return ResponseEntity.status(500).body(BaseResponseBody.of(404, "방이 없습니다."));
//    }
//    return ResponseEntity.status(200).body(BaseResponseBody.of(200, "성공"));
//  }
//
//  @GetMapping("/")
//  @ApiOperation(value = "전체 방 보기")
//  @ApiResponses({@ApiResponse(code = 200, message = "성공"),
//      @ApiResponse(code = 401, message = "인증 실패"),
//      @ApiResponse(code = 404, message = "사용자 없음"),
//      @ApiResponse(code = 409, message = "이미 존재하는 유저"),
//      @ApiResponse(code = 500, message = "서버 오류")})
//  public ResponseEntity<List<RoomGetRes>> showRooms() {
//
//    List<RoomGetRes> rooms = roomService.findAll();
//    for (RoomGetRes item : rooms) {
//      item.setParticipants(participantService.getParticipantByRoomId(item.getRoom_id()));
//    }
//    return new ResponseEntity<List<RoomGetRes>>(rooms, HttpStatus.OK);
//  }
//
  @GetMapping("/user/{userId}")
  @ApiOperation(value = "사용자 아이디가 참가자로 포함된 전체 방 보기")
  @ApiResponses({@ApiResponse(code = 200, message = "성공"),
      @ApiResponse(code = 401, message = "인증 실패"),
      @ApiResponse(code = 404, message = "사용자 없음"),
      @ApiResponse(code = 409, message = "이미 존재하는 유저"),
      @ApiResponse(code = 500, message = "서버 오류")})
  public ApiResponseDto<List<RoomGetRes>> showRoomsByOauthId(@PathVariable("userId") Long userId) {
    List<RoomGetRes> rooms = null;
    try {
      //Long userId = userService.getUserIdByOauthId(oauthId);
      rooms = roomService.findbyuser(userId);
      for (RoomGetRes item : rooms) {
        item.setParticipants(participantService.getParticipantByRoomId(item.getRoomId()));
      }
      return ApiResponseDto.success(rooms);
    } catch (Exception e) {
      log.error(String.valueOf(e));
    }

    return ApiResponseDto.fail(rooms, "방 정보를 가져올 수 없습니다.");
  }
//
//  @GetMapping("/onlive/{roomId}")
//  @ApiOperation(value = "방이 현재 활동중인지 보기")
//  @ApiResponses({@ApiResponse(code = 200, message = "성공"),
//      @ApiResponse(code = 401, message = "인증 실패"),
//      @ApiResponse(code = 404, message = "사용자 없음"),
//      @ApiResponse(code = 409, message = "이미 존재하는 유저"),
//      @ApiResponse(code = 500, message = "서버 오류")})
//  public ResponseEntity<? extends BaseResponseBody> isOnLive(@PathVariable("roomId") int roomId) {
//    try {
//      Rooms room = roomService.getRoom(roomId);
//      if (room.getOnLive()) {
//        return ResponseEntity.status(200).body(BaseResponseBody.of(200, "방이 활동중입니다."));
//      } else {
//        return ResponseEntity.status(404).body(BaseResponseBody.of(404, "방이 활동중이지 않습니다."));
//      }
//    } catch (Exception e) {
//      e.printStackTrace();
//      return ResponseEntity.status(500).body(BaseResponseBody.of(500, "Fail"));
//
//    }
//  }
//
//  @PutMapping("/onlive")
//  @ApiOperation(value = "방의 활동 상태를 true/false로 변경하기")
//  @ApiResponses({@ApiResponse(code = 200, message = "성공"),
//      @ApiResponse(code = 401, message = "인증 실패"),
//      @ApiResponse(code = 404, message = "사용자 없음"),
//      @ApiResponse(code = 409, message = "이미 존재하는 유저"),
//      @ApiResponse(code = 500, message = "서버 오류")})
//  public ResponseEntity<? extends BaseResponseBody> setOnLive(
//      @RequestBody @ApiParam(value = "방의 onLive 상태 변경", required = true) RoomOnLiveReq roomInfo) {
//    try {
//      log.info("[set on live] roomInfo: {}", roomInfo);
//      roomService.setRoomOnLive(roomInfo);
//
//      return ResponseEntity.status(200).body(BaseResponseBody.of(200, "방 onLive 상태가 변경되었습니다."));
//    } catch (Exception e) {
//      e.printStackTrace();
//      return ResponseEntity.status(500).body(BaseResponseBody.of(500, "Fail"));
//
//    }
//  }
//
//  @GetMapping("/{roomId}")
//  @ApiOperation(value = "방 하나의 정보 보기")
//  @ApiResponses({@ApiResponse(code = 200, message = "성공"),
//      @ApiResponse(code = 401, message = "인증 실패"),
//      @ApiResponse(code = 404, message = "사용자 없음"),
//      @ApiResponse(code = 409, message = "이미 존재하는 유저"),
//      @ApiResponse(code = 500, message = "서버 오류")})
//  public ResponseEntity<RoomGetRes> showRoomone(@PathVariable("roomId") int roomId) {
//    Rooms room = roomService.getRoom(roomId);
//    List<ParticipantGetRes> participants = participantService.getParticipantByRoomId(roomId);
//    RoomGetRes roomget = new RoomGetRes(room.getName(), room.getDescription(),
//        room.getStartTime().toLocalDateTime(), room.getUsers().getUserId(),
//        room.getUsers().getName(),
//        room.getRoomId());
//    if (room.getEndTime() == null) {
//      roomget.setEndTime(null);
//    } else {
//      roomget.setEndTime(room.getEndTime().toLocalDateTime());
//    }
//    roomget.setParticipants(participants);
//
//    return new ResponseEntity<RoomGetRes>(roomget, HttpStatus.OK);
//  }
}
