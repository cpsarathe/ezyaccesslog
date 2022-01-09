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
