package com.sht.apimodels;

import com.google.gson.annotations.SerializedName;

public class Payload {
    @SerializedName("tsync_id")
    private String tsyncId;
    @SerializedName("name")
    private String name;
    @SerializedName("email")
    private String email;
    @SerializedName("phone")
    private String phone;
    @SerializedName("full_address")
    private String fullAddress;
    @SerializedName("name_of_university")
    private String nameOfUniversity;
    @SerializedName("graduation_year")
    private Integer graduationYear;
    @SerializedName("cgpa")
    private Float cgpa;
    @SerializedName("experience_in_months")
    private Integer experienceInMonths;
    @SerializedName("current_work_place_name")
    private String currentWorkPlaceName;
    @SerializedName("applying_in")
    private String applyingIn;
    @SerializedName("expected_salary")
    private Integer expectedSalary;
    @SerializedName("field_buzz_reference")
    private String fieldBuzzReference;
    @SerializedName("github_project_url")
    private String githubProjectUrl;
    @SerializedName("cv_file")
    private CvFile cvFile;
    @SerializedName("on_spot_update_time")
    private Long onSpotUpdateTime;
    @SerializedName("on_spot_creation_time")
    private Long onSpotCreationTime;

    public String getTsyncId() {
        return tsyncId;
    }

    public void setTsyncId(String tsyncId) {
        this.tsyncId = tsyncId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getFullAddress() {
        return fullAddress;
    }

    public void setFullAddress(String fullAddress) {
        fullAddress = fullAddress.trim();
        if(!fullAddress.isEmpty()){
            this.fullAddress = fullAddress;
        }
    }

    public String getNameOfUniversity() {
        return nameOfUniversity;
    }

    public void setNameOfUniversity(String nameOfUniversity) {
        this.nameOfUniversity = nameOfUniversity;
    }

    public Integer getGraduationYear() {
        return graduationYear;
    }

    public void setGraduationYear(String graduationYearStr) {
        graduationYearStr = graduationYearStr.trim();
        if(!graduationYearStr.isEmpty()){
            this.graduationYear = Integer.parseInt(graduationYearStr);
        }
    }

    public float getCgpa() {
        return cgpa;
    }

    public void setCgpa(String cgpaStr) {
        cgpaStr = cgpaStr.trim();
        if(!cgpaStr.isEmpty()){
            this.cgpa = Float.parseFloat(cgpaStr);
        }
    }

    public Integer getExperienceInMonths() {
        return experienceInMonths;
    }

    public void setExperienceInMonths(String experienceInMonthsStr) {
        experienceInMonthsStr = experienceInMonthsStr.trim();
        if(!experienceInMonthsStr.isEmpty()){
            this.experienceInMonths = Integer.parseInt(experienceInMonthsStr);
        }
    }

    public String getCurrentWorkPlaceName() {
        return currentWorkPlaceName;
    }

    public void setCurrentWorkPlaceName(String currentWorkPlaceName) {
        currentWorkPlaceName = currentWorkPlaceName.trim();
        if(!currentWorkPlaceName.isEmpty()){
            this.currentWorkPlaceName = currentWorkPlaceName;
        }
    }

    public String getApplyingIn() {
        return applyingIn;
    }

    public void setApplyingIn(String applyingIn) {
        this.applyingIn = applyingIn;
    }

    public Integer getExpectedSalary() {
        return expectedSalary;
    }

    public void setExpectedSalary(Integer expectedSalary) {
        this.expectedSalary = expectedSalary;
    }

    public String getFieldBuzzReference() {
        return fieldBuzzReference;
    }

    public void setFieldBuzzReference(String fieldBuzzReference) {
        fieldBuzzReference = fieldBuzzReference.trim();
        if(!fieldBuzzReference.isEmpty()){
            this.fieldBuzzReference = fieldBuzzReference;
        }
    }

    public String getGithubProjectUrl() {
        return githubProjectUrl;
    }

    public void setGithubProjectUrl(String githubProjectUrl) {
        this.githubProjectUrl = githubProjectUrl;
    }

    public CvFile getCvFile() {
        return cvFile;
    }

    public void setCvFile(CvFile cvFile) {
        this.cvFile = cvFile;
    }

    public Long getOnSpotUpdateTime() {
        return onSpotUpdateTime;
    }

    public void setOnSpotUpdateTime() {
        this.onSpotUpdateTime = System.currentTimeMillis();
    }

    public void setOnSpotUpdateTime(Long onSpotUpdateTime) {
        this.onSpotUpdateTime = onSpotUpdateTime;
    }

    public Long getOnSpotCreationTime() {
        return onSpotCreationTime;
    }

    public void setOnSpotCreationTime(Long onSpotCreationTime) {
        this.onSpotCreationTime = onSpotCreationTime;
    }
}
