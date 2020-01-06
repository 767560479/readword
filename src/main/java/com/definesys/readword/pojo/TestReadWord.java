package com.definesys.readword.pojo;

import com.definesys.mpaas.query.json.MpaasDateDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.util.Date;

import com.definesys.mpaas.query.model.MpaasBasePojo;
import com.definesys.mpaas.query.json.MpaasDateSerializer;
import com.definesys.mpaas.query.annotation.*;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

/**
 * @Copyright: Shanghai Definesys Company.All rights reserved.
 * @Description:
 * @author: fei
 * @since: 2019-08-20
 * @history: 1.2019-08-20 created by fei
 */
@Table(value = "TEST_READ_WORD")
public class TestReadWord extends MpaasBasePojo {

    @RowID(sequence = "TEST_READ_WORD_S", type = RowIDType.AUTO)
    private Long id;

    @SystemColumn(SystemColumnType.CREATE_BY)
    @Column(value = "CREATED_BY")
    private String createdBy;

    @JsonSerialize(using = MpaasDateSerializer.class)
    @JsonDeserialize(using = MpaasDateDeserializer.class)
    @SystemColumn(SystemColumnType.CREATE_ON)
    @Column(value = "CREATION_DATE")
    private Date creationDate;

    @SystemColumn(SystemColumnType.LASTUPDATE_BY)
    @Column(value = "LAST_UPDATED_BY")
    private String lastUpdatedBy;

    @JsonSerialize(using = MpaasDateSerializer.class)
    @JsonDeserialize(using = MpaasDateDeserializer.class)
    @SystemColumn(SystemColumnType.LASTUPDATE_ON)
    @Column(value = "LAST_UPDATE_DATE")
    private Date lastUpdateDate;

    @SystemColumn(SystemColumnType.OBJECT_VERSION)
    @Column(value = "OBJECT_VERSION_NUMBER")
    private Long objectVersionNumber;

    @Style(displayName = "单位")
    private String unit;

    @Style(displayName = "岗位")
    private String job;

    @Style(displayName = "姓名")
    private String name;

    @Style(displayName = "性别")
    private String sex;

    @Style(displayName = "出生年月")
    private String birthday;

    @Style(displayName = "籍贯")
    private String place;

    @Style(displayName = "学历")
    private String education;

    @Style(displayName = "学校")
    private String school;

    @Style(displayName = "专业")
    private String major;

    @Column(value = "GRADUATION_DATE")
    @Style(displayName = "毕业时间")
    private String graduationDate;

    @Style(displayName = "备注")
    private String note;


    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCreatedBy() {
        return this.createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Date getCreationDate() {
        return this.creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public String getLastUpdatedBy() {
        return this.lastUpdatedBy;
    }

    public void setLastUpdatedBy(String lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
    }

    public Date getLastUpdateDate() {
        return this.lastUpdateDate;
    }

    public void setLastUpdateDate(Date lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
    }

    public Long getObjectVersionNumber() {
        return this.objectVersionNumber;
    }

    public void setObjectVersionNumber(Long objectVersionNumber) {
        this.objectVersionNumber = objectVersionNumber;
    }

    public String getUnit() {
        return this.unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getJob() {
        return this.job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return this.sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getBirthday() {
        return this.birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getPlace() {
        return this.place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getEducation() {
        return this.education;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public String getSchool() {
        return this.school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public String getMajor() {
        return this.major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public String getGraduationDate() {
        return this.graduationDate;
    }

    public void setGraduationDate(String graduationDate) {
        this.graduationDate = graduationDate;
    }

    public String getNote() {
        return this.note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    @Override
    public String toString() {
        return "TestReadWord{" +
                "id=" + id +
                ", createdBy='" + createdBy + '\'' +
                ", creationDate=" + creationDate +
                ", lastUpdatedBy='" + lastUpdatedBy + '\'' +
                ", lastUpdateDate=" + lastUpdateDate +
                ", objectVersionNumber=" + objectVersionNumber +
                ", unit='" + unit + '\'' +
                ", job='" + job + '\'' +
                ", name='" + name + '\'' +
                ", sex='" + sex + '\'' +
                ", birthday='" + birthday + '\'' +
                ", place='" + place + '\'' +
                ", education='" + education + '\'' +
                ", school='" + school + '\'' +
                ", major='" + major + '\'' +
                ", graduationDate='" + graduationDate + '\'' +
                ", note='" + note + '\'' +
                '}';
    }
}