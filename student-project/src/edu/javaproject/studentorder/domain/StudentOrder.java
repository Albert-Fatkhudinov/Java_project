package edu.javaproject.studentorder.domain;

import edu.javaproject.studentorder.other.Adult;

public class StudentOrder {

   private long studentOrderID;
   private Adult husband;
   private Adult wife;
   private Child child;

   public long getStudentOrderID() {
      return studentOrderID;
   }

   public void setStudentOrderID(long studentOrderID) {
      this.studentOrderID = studentOrderID;
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

   public Child getChild() {
      return child;
   }

   public void setChild(Child child) {
      this.child = child;
   }
}
