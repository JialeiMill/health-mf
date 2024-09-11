create schema health_mf collate utf8mb4_0900_ai_ci;

create table health_card_mf
(
    id           bigint auto_increment comment '主键'
        primary key,
    card_pass    varchar(30)                        not null comment '卡密',
    status       int      default 0                 null comment '状态 0 正常 1 使用 2 绑定',
    bind_user_id bigint                             null comment '绑定人用户ID',
    create_time  datetime default CURRENT_TIMESTAMP null comment '创建时间',
    bind_time    datetime                           null comment '绑定时间',
    bind_phone   varchar(15)                        null comment '绑定手机号'
)
    comment '健康卡-觅方';

create unique index health_card_mf_card_pass_uindex
    on health_card_mf (card_pass);


create table user_info
(
    id          bigint auto_increment comment '主键',
    union_id    varchar(50)            null comment '唯一ID',
    real_name   varchar(100)           null comment '真实姓名',
    id_card     varchar(20)            null comment '身份证号码',
    create_time datetime default NOW() null,
    real_time   datetime               null comment '实名时间',
    constraint user_info_pk
        primary key (id)
)
    comment '用户信息';

create index user_info_open_id_index
    on user_info (open_id);

create index user_info_union_id_index
    on user_info (union_id);

