/**
 * 
 */
package net.haibo.utils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


/**
 * @author HIBER (hiber.luo@foxmail.com) Initial Created at 2014-6-24
 * It defines many utility functions for array operations
 * The major functions:
 * - it provides a simple array builder, it implements
 *  add, subtract, and simple filter operations.
 * - you can customize the match rule of array filter operation through 
 *  <code>Matchable<T>, Rule<K, V></code> interface.
 * - it also provides a string's implementation of match method with reg-ex. 
 * which is :
 *  <code>sMatchWith(), sMatch() and sRule()</code>
 *  
 */
public class ArrayOps {
    /** define a matchable action interface */
    public interface IMatchable<T> {
        public boolean match(T t);
    }

    /** define the match rule entry, 
     * it includes a pattern and a target on which previous patterns invokes */
    public static class Rule<K, V> {
        public K target; public V pattern;
    }

    /** define a composited rules action interface,
     * it accept a set of rule entries for matching operation.
     * K is target type
     * V is the pattern type */
    public interface IMatchRule<K, V> {
        public boolean accept(Rule<K, V>...entries);
    }

    /** create a array builder through the array  */
    public static <T> ArrayBuilder<T> arrayBuilder(List<T> the) {
        return new ArrayBuilder<T>(the);
    }

    /** we recommend you use Array Builder to operate the array,
     *  it provides add, subtract, filter operations. */
    public static class ArrayBuilder<T> {
        private final List<T> the;
        public ArrayBuilder(List<T> the) {
            // got one copy
            this.the = new ArrayList<T>(the);
        }

        /** Subtract that(array) from the(array), return builder with left the(array)  */
        public ArrayBuilder<T> subtract(List<T> that) {
            the.removeAll(that);
            return this;
        }

        /** Add that(array) from the(array), return builder with appended the(array)  */
        public ArrayBuilder<T> add(List<T> that) {
            the.addAll(that);
            return this;
        }

        /** filter the(array) by the match rule, return builder with left the(array)  */
        public ArrayBuilder<T> filter(IMatchable<T> matchable) {
            assert matchable != null;

            Iterator<T> iter = the.iterator();
            while (iter.hasNext()) {
                T t = iter.next();
                if (!matchable.match(t)) {
                    iter.remove();
                }
            }
            return this;
        }

        public List<T> build() {
            return this.the;
        }
    }

    /** return a string kind of matchable implementation with reg-ex */
    public static IMatchable<String> sMatchWith(final String pattern) {
        return new IMatchable<String>() {
            @SuppressWarnings("unchecked")
            @Override public boolean match(String t) {
                return sMatch().accept(sRule(t, pattern));
            }
        };
    }

    /** create and return a string kind of rule entry */
    public static Rule<String, String> sRule(String t, String p) {
        Rule<String, String> entry = new Rule<String, String>();
        entry.target = t; entry.pattern = p;
        return entry;
    }

    /** create and return a composited rules action based on string kind of rule entry */
    public static IMatchRule<String, String> sMatch() {
        return new IMatchRule<String, String>() {
            @Override
            public boolean accept(Rule<String, String>... entries) {
                assert entries != null;
                for (Rule<String, String> rule : entries) {
                    if (rule.target != null 
                        && !rule.target.isEmpty()
                        && rule.pattern != null
                        && !rule.pattern.isEmpty()
                        && !rule.target.matches(rule.pattern)) {
                        return false;
                    }
                }
                return true;
            }
        };
    }

}