package com.crustafrica.assessment.controller;

import com.crustafrica.assessment.dto.output.StandardResponseDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Controller {

    @Autowired
    private HttpServletResponse response;

    @Autowired
    private HttpServletRequest request;

    <T extends StandardResponseDTO> T responseWithUpdatedHttpStatus(T responseDTO) {
        switch (responseDTO.getStatus()) {
            case SUCCESS:
                response.setStatus(HttpStatus.OK.value());
                break;
            case NO_CONTENT:
                response.setStatus(HttpStatus.NO_CONTENT.value());
                break;
            case CONFLICT:
                response.setStatus(HttpStatus.CONFLICT.value());
                break;
            case CREATED:
                response.setStatus(HttpStatus.CREATED.value());
                break;
            case NOT_FOUND:
                response.setStatus(HttpStatus.NOT_FOUND.value());
                break;
            case FORBIDDEN:
                response.setStatus(HttpStatus.FORBIDDEN.value());
                break;
            case PAYMENT_REQUIRED:
                response.setStatus(HttpStatus.PAYMENT_REQUIRED.value());
                break;
            default:
                response.setStatus(HttpStatus.BAD_REQUEST.value());
        }

        //log.info("endpoint: " + request.getRequestURI());
        try {
            String resp = new ObjectMapper().writeValueAsString(responseDTO);
            //log.info("status: {}, returning: {} " , response.getStatus(), resp.length() > 100 ? resp.substring(0,100) : resp);

        } catch (JsonProcessingException e) {
            //log.error("couldn't echo response");
        }

        return responseDTO;
    }
}
