CREATE TABLE [meeting] (
[_id] INTEGER  NOT NULL PRIMARY KEY AUTOINCREMENT,
[title] NVARCHAR(256)  NULL,
[leader_id] INTEGER  NULL,
[first_start_date] NUMERIC(20)  NULL,
[start_date] NUMERIC(20)  NULL,
[stop_date] NUMERIC(20)  NULL,
[create_date] NUMERIC(20)  NULL,
[duration] NUMERIC(20)  NULL,
[attendee_ids] VARCHAR(1024) NULL,
[is_template] INTEGER NOT NULL DEFAULT 0,
[_order] INTEGER NULL
);

CREATE TABLE [topic] (
[_id] INTEGER  NOT NULL PRIMARY KEY AUTOINCREMENT,
[title] NVARCHAR(255)  NULL,
[meeting_id] INTEGER  NOT NULL,
[_order] INTEGER  NULL
);

CREATE TABLE [record] (
[_id] INTEGER  NOT NULL PRIMARY KEY AUTOINCREMENT,
[text] TEXT  NULL,
[type] INTEGER  NULL,
[topic_id] VARCHAR(36)  NOT NULL,
[date_time] NUMERIC(20)  NULL,
[responsible_ids] VARCHAR(512)  NULL,
[reporter_ids] VARCHAR(512) NULL
);

CREATE TABLE [attachment] (
[_id] INTEGER  NOT NULL PRIMARY KEY AUTOINCREMENT,
[record_id] INTEGER  NOT NULL,
[file_name] TEXT  NULL,
[uri] VARCHAR(255) NULL,
[type] INTEGER  NULL,
[media_id] INTEGER  NULL
);

CREATE INDEX [record_type] ON [record]([type]  ASC);
CREATE INDEX [record_topic_id] ON [record]([topic_id]  ASC);