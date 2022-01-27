package com.ezy.access.model;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.Date;

@Data
@EqualsAndHashCode(of = {"id"})
@Entity
@Table(name = "access_log_v2")
@Builder
public class AccessLogModelV2 {
    private static final long serialVersionUID = 1L;
    @Column(name = "id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    @Column(name = "a_remote_ip_address")
    protected String remoteIpAddress;

    @Column(name = "A_local_ip_address")
    protected String localIpAddress;

    @Column(name = "b_bytes_sent_default")
    protected long bytesSentWithDefault;

    @Column(name = "B_bytes_sent")
    protected long bytesSent;

    @Column(name = "h_remote_host_name")
    protected String remoteHostName;

    @Column(name = "H_request_protocol")
    protected String requestProtocol;

    @Column(name = "l_remote_username")
    protected String remoteUserIdentd;

    @Column(name = "m_request_method")
    protected String requestMethod;

    @Column(name = "p_local_port")
    protected int localPort;

    @Column(name = "q_query_string")
    protected String queryString;

    @Column(name = "r_request_element")
    protected String request;

    @Column(name = "s_status_code")
    protected int statusCode;

    @Column(name = "S_session_id")
    protected String sessionId;

    @Column(name = "t_date_time")
    protected Date dateTime;

    @Column(name = "u_remote_user_authenticated")
    protected String remoteUserAuthenticated;

    @Column(name = "U_request_url_path")
    protected String requestedURLPath;

    @Column(name = "v_local_server_name")
    protected String localServerName;

    @Column(name = "D_time_to_process_request")
    protected long timeToProcessRequestInMillis;

    @Column(name = "T_time_to_process_request")
    protected long timeToProcessRequestInSeconds;

    @Column(name = "F_time_to_commit_response")
    protected long timeToCommitResponseInMillis;

    @Column(name = "I_current_request_thread_name")
    protected String currentRequestThreadName;

    @Column(name = "X_connection_status")
    protected String connectionStatus;

    @Column(name = "i_x_forwarded_for")
    protected String iXForwardedFor;

    @Column(name = "i_x_forwarded_proto")
    protected String iXForwardedProto;

    @Column(name = "i_user_agent")
    protected String iUserAgent;

    @Column(name = "i_host_name")
    protected String iHostName;

    @Column(name = "created_date")
    protected Date createdDate;

    @Column(name = "app_name")
    protected String appName;

    @Column(name = "file_name")
    protected String fileName;

    @Column(name = "request_key")
    protected String requestKey;
}
