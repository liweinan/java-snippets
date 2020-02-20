package io.alchemystudio.sandbox;

public class ObjectHolder<T> {
   private T object;

   public T getObject() {
      return object;
   }

   public void setObject(T object) {
      this.object = object;
   }

   public ObjectHolder(T object) {
      this.object = object;
   }
}
