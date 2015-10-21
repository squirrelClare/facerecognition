package svm_test;

import static org.junit.Assert.*;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import org.junit.Test;

public class RandomTest {

	@Test
	public void test() {
		Set<Integer> set=new HashSet<Integer>();
		Random random=new Random();
		
		while (set.size()<5) {
			set.add(random.nextInt(236)%10+1);
		}
		for (Integer integer : set) {
			System.out.println(integer);
		}
		
		Set<Integer> set_a=new HashSet<Integer>();
		for (int i = 1; i < 11; i++) {
			set_a.add(i);
		}
		System.out.println("*********************");
		set_a.removeAll(set);
		for (Integer integer : set_a) {
			System.out.println(integer);
		}
		double[] s=new double[10];
	}

}
