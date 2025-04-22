package io.weli.sandbox;

public class PlayWithObjectHolder {

   public static void main(String[] args) {
      StringObject stringObject = new StringObject("test");
      ObjectHolder<StringObject> holder = new ObjectHolder<>(stringObject);
      System.out.println(holder.getObject());
   }

   private static void modifyObjectInHolder(ObjectHolder<StringObject> holder) {
      StringObject obj = holder.getObject();
      obj.setStr("bar");
   }
}
