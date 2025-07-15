package shareit.request;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import shareit.request.dto.RequestDto;

import static shareit.common.Constants.USER_ID_HEADER;

@Controller
@RequestMapping(path = "/requests")
@RequiredArgsConstructor
@Slf4j
@Validated
public class RequestController {
    private final RequestClient requestClient;

    @GetMapping
    public ResponseEntity<Object> getRequests(@RequestHeader(USER_ID_HEADER) long userId) {
        log.info("Get requests, userId={}", userId);
        return requestClient.getRequests(userId);
    }

    @GetMapping("/{requestId}")
    public ResponseEntity<Object> getRequest(@PathVariable Long requestId,
                                           @RequestHeader(USER_ID_HEADER) long userId) {
        log.info("Get request {}, userId={}", requestId, userId);
        return requestClient.getRequest(requestId, userId);
    }

    @PostMapping
    public ResponseEntity<Object> createRequest(@RequestHeader(USER_ID_HEADER) long userId,
                                              @RequestBody @Valid RequestDto requestDto) {
        log.info("Creating request {}, userId={}", requestDto, userId);
        return requestClient.createRequest(userId, requestDto);
    }
} 