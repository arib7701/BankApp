package com.bankapp.exception;


import lombok.Data;

@Data
public class ErrorInfo {


    private String code;
    private String message;

}
