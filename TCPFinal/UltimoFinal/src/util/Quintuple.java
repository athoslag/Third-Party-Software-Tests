/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

/**
 *
 * @author Andy Ruiz
 * @param <X>
 * @param <Y>
 * @param <V>
 * @param <Z>
 * @param <W>
 */
public class Quintuple<X, Y, V,Z, W> { 
  public final X x; 
  public final Y y; 
  public final Z z; 
  public final V v;
  public final W w; 
  
  
  public Quintuple(X x, Y y, Z z,V v, W w) { 
    this.x = x; 
    this.y = y; 
    this.z = z; 
    this.v = v;
    this.w = w;
  } 
} 
    

