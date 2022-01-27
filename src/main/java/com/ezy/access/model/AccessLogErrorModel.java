package com.ezy.access.model;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.Date;

@Data
@EqualsAndHashCode(of = {"id"})
@Entity
@Table(name = "access_log_error")
@Builder
public class AccessLogErrorModel {
    private static final long serialVersionUID = 1L;
    @Column(name = "id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;
    @Column(name = "log_record")
    protected String accessLogRecord;
    @Column(name = "created_date")
    protected Date createdDate;
    @Column(name = "app_name")
    protected String appName;
    @Column(name = "file_name")
    protected String fileName;
    @Column(name = "request_key")
    protected String requestKey;

}
