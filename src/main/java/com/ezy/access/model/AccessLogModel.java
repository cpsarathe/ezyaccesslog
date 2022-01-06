package com.ezy.access.model;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Data
@EqualsAndHashCode(of = {"id"})
@Entity
@Table(name = "access_log")
@Builder
public class AccessLogModel {
    private static final long serialVersionUID = 1L;
    @Column(name = "id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;
    @Column(name = "h_remote_host")
    protected String hRemoteHost;
    @Column(name = "l_user_name")
    protected String lUserName;
    @Column(name = "t_date_time")
    protected Date tDateTime;
    @Column(name = "u_remote_user")
    protected String uRemoteUser;
    @Column(name = "r_request_uri")
    protected String rRequestUri;
    @Column(name = "s_http_status_code")
    protected int sHttpStatusCode;
    @Column(name = "b_response_size")
    protected int bResponseSize;
    @Column(name = "i_x_forwarded_for")
    protected String iXForwardedFor;
    @Column(name = "i_x_forwarded_proto")
    protected String iXForwardedProto;
    @Column(name = "created_date")
    protected Date createdDate;

}
