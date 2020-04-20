package edu.javaproject.studentorder.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class StudentOrder {

   private long studentOrderId;
   private LocalDateTime studentOrderDate;
   private StudentOrderStatus studentOrderStatus;
   private Adult husband;
   private Adult wife;
   private List<Child> children;
   private String marriageCertificateId;
   private RegisterOffice marriageOffice;
   private LocalDate marriageDate;

   public long getStudentOrderId() {
      return studentOrderId;
   }

   public void setStudentOrderId(long studentOrderId) {
      this.studentOrderId = studentOrderId;
   }

   public LocalDateTime getStudentOrderDate() {
      return studentOrderDate;
   }

   public void setStudentOrderDate(LocalDateTime studentOrderDate) {
      this.studentOrderDate = studentOrderDate;
   }

   public StudentOrderStatus getStudentOrderStatus() {
      return studentOrderStatus;
   }

   public void setStudentOrderStatus(StudentOrderStatus studentOrderStatus) {
      this.studentOrderStatus = studentOrderStatus;
   }

   public Adult getHusband() {
      return husband;
   }

   public void setHusband(Adult husband) {
      this.husband = husband;
   }

   public Adult getWife() {
      return wife;
   }

   public void setWife(Adult wife) {
      this.wife = wife;
   }

   public List<Child> getChildren() {
      return children;
   }

   public void addChild(Child child) {
      if (children == null) {
         children = new ArrayList<>(5);
      }
      children.add(child);
   }

   public String getMarriageCertificateId() {
      return marriageCertificateId;
   }

   public void setMarriageCertificateId(String marriageCertificateId) {
      this.marriageCertificateId = marriageCertificateId;
   }

   public RegisterOffice getMarriageOffice() {
      return marriageOffice;
   }

   public void setMarriageOffice(RegisterOffice marriageOffice) {
      this.marriageOffice = marriageOffice;
   }

   public void setChildren(List<Child> children) {
      this.children = children;
   }

   public LocalDate getMarriageDate() {
      return marriageDate;
   }

   public void setMarriageDate(LocalDate marriageDate) {
      this.marriageDate = marriageDate;
   }

   @Override
   public String toString() {
      return "StudentOrder{" +
              "studentOrderId=" + studentOrderId +
              ", studentOrderDate=" + studentOrderDate +
              ", studentOrderStatus=" + studentOrderStatus +
              ", husband=" + husband +
              ", wife=" + wife +
              ", children=" + children +
              ", marriageCertificateId='" + marriageCertificateId + '\'' +
              ", marriageOffice=" + marriageOffice +
              ", marriageDate=" + marriageDate +
              '}';
   }
}
