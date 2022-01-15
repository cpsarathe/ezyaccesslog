CREATE SCHEMA `ezyaccess` DEFAULT CHARACTER SET utf8 ;

CREATE TABLE `access_log` (
`id` bigint(20) NOT NULL AUTO_INCREMENT,
`h_remote_host` varchar(255) DEFAULT NULL,
`l_user_name` varchar(255) DEFAULT NULL,
`t_date_time` datetime DEFAULT NULL,
`u_remote_user`  varchar(255) DEFAULT NULL,
`r_request_uri` varchar(255) DEFAULT NULL,
`s_http_status_code` int(4) DEFAULT -1,
`b_response_size` int(16) DEFAULT NULL,
`i_x_forwarded_for` varchar(255) DEFAULT NULL,
`i_x_forwarded_proto` varchar(255) DEFAULT NULL,
`created_date` datetime DEFAULT NULL,
PRIMARY KEY (`id`),
KEY `access_log_id_idx` (`id`),
KEY `access_log_r_request_uri_idx` (`r_request_uri`),
KEY `access_log_s_http_status_code_idx` (`s_http_status_code`),
KEY `access_log_t_date_time_idx` (`t_date_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

ALTER TABLE `ezyaccess`.`access_log`
    ADD COLUMN `app_name` VARCHAR(255) NULL DEFAULT NULL COMMENT '' AFTER `created_date`;

ALTER TABLE `ezyaccess`.`access_log`
    ADD INDEX `access_log_app_name_idx` (`app_name` ASC)  COMMENT '';

ALTER TABLE `ezyaccess`.`access_log`
    ADD COLUMN `file_name` VARCHAR(255) NULL DEFAULT NULL COMMENT '' AFTER `app_name`,
ADD INDEX `access_log_file_name_idx` (`file_name` ASC)  COMMENT '';


/** v2 table structure to support more robust format   **/
CREATE TABLE `access_log_v2` (
                                 `id` bigint(20) NOT NULL AUTO_INCREMENT,
                                 `request_key` varchar(255) DEFAULT NULL,
                                 `a_remote_ip_address` varchar(255) DEFAULT NULL,
                                 `A_local_ip_address` varchar(255) DEFAULT NULL,
                                 `b_bytes_sent_default` int(16) DEFAULT NULL,
                                 `B_bytes_sent` int(16) DEFAULT NULL,
                                 `h_remote_host_name`  varchar(255) DEFAULT NULL,
                                 `H_request_protocol`  varchar(255) DEFAULT NULL,
                                 `l_remote_username`  varchar(255) DEFAULT NULL,
                                 `m_request_method`  varchar(255) DEFAULT NULL,
                                 `p_local_port`  int(16) DEFAULT NULL,
                                 `q_query_string`  varchar(255) DEFAULT NULL,
                                 `r_request_element`  varchar(255) DEFAULT NULL,
                                 `s_status_code`  int(16) DEFAULT NULL,
                                 `S_session_id`  varchar(255) DEFAULT NULL,
                                 `t_date_time` datetime DEFAULT NULL,
                                 `u_remote_user_authenticated` varchar(255) DEFAULT NULL,
                                 `U_request_url_path`  varchar(255) DEFAULT NULL,
                                 `v_local_server_name`  varchar(255) DEFAULT NULL,
                                 `D_time_to_process_request`  int(16) DEFAULT NULL,
                                 `T_time_to_process_request`  int(16) DEFAULT NULL,
                                 `F_time_to_commit_response`  int(16) DEFAULT NULL,
                                 `I_current_request_thread_name`  varchar(255) DEFAULT NULL,
                                 `X_connection_status`  varchar(255) DEFAULT NULL,
                                 `i_x_forwarded_for` varchar(255) DEFAULT NULL,
                                 `i_x_forwarded_proto` varchar(255) DEFAULT NULL,
                                 `i_host_name` varchar(255) DEFAULT NULL,
                                 `created_date` datetime DEFAULT NULL,
                                 `app_name` varchar(255) DEFAULT NULL,
                                 `file_name` varchar(255) DEFAULT NULL,
                                 PRIMARY KEY (`id`),
                                 KEY `access_log_v2_id_idx` (`id`),
                                 KEY `access_log_v2_request_key_idx`(`request_key`),
                                 KEY `access_log_v2_app_name_idx` (`app_name`),
                                 KEY `access_log_v2_file_name_idx` (`file_name`),
                                 KEY `access_log_v2_r_request_uri_idx` (`r_request_element`),
                                 KEY `access_log_v2_s_http_status_code_idx` (`s_status_code`),
                                 KEY `access_log_v2_t_date_time_idx` (`t_date_time`),
                                 KEY `access_log_v2_i_host_name_idx` (`i_host_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;