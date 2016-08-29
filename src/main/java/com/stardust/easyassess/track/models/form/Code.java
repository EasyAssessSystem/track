package com.stardust.easyassess.track.models.form;


public class Code extends FormElement {
    private TestSubject subject;
    private String subjectGuid;
    private String codeNumber;
    private String codeName;

    private CodeGroup codeGroup;

    public CodeGroup getCodeGroup() {
        return codeGroup;
    }

    public void setCodeGroup(CodeGroup codeGroup) {
        this.codeGroup = codeGroup;
    }

    public TestSubject getSubject() {
        return subject;
    }

    public void setSubject(TestSubject subject) {
        this.subject = subject;
    }

    public String getSubjectGuid() {
        return subjectGuid;
    }

    public void setSubjectGuid(String subjectGuid) {
        this.subjectGuid = subjectGuid;
    }

    public String getCodeNumber() {
        return codeNumber;
    }

    public void setCodeNumber(String codeNumber) {
        this.codeNumber = codeNumber;
    }

    public String getCodeName() {
        return codeName;
    }

    public void setCodeName(String codeName) {
        this.codeName = codeName;
    }
}
