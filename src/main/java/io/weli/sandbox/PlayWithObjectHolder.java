package io.weli.sandbox;

public class PlayWithObjectHolder {

   public static void main(String[] args) throws Exception {

      ObjectHolder<StringObject> holder = new ObjectHolder(new StringObject("foo"));

      modifyObjectInHolder(holder);

      System.out.println(holder.getObject().getStr());
   }

   private static void modifyObjectInHolder(ObjectHolder<StringObject> holder) {
      StringObject obj = holder.getObject();
      obj.setStr("bar");
   }
}
