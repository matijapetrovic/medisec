package com.medisec.hospitalservice.alarms.service_log_alarm;

import com.google.gson.annotations.SerializedName;

public enum LogType {
    @SerializedName("NORMAL")
    NORMAL,
    @SerializedName("BRUTE_FORCE_ATTACK")
    BRUTE_FORCE_ATTACK,
    @SerializedName("DOS_ATTACK")
    DOS_ATTACK,
    @SerializedName("MALICIOUS_IP")
    MALICIOUS_IP,
    @SerializedName("INACTIVE_ACCOUNT")
    INACTIVE_ACCOUNT,
    @SerializedName("VALID_LOGIN")
    VALID_LOGIN,
    @SerializedName("FAILED_LOGIN")
    FAILED_LOGIN,
    @SerializedName("ERROR")
    ERROR
}