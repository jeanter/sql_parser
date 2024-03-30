package com.jsonyang.parser;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import com.jsonyang.parser.token.Keyword;
import com.jsonyang.parser.token.Symbol;
import com.jsonyang.parser.token.TokenType;

public class Test {

	public static void main(String[] args) {
		Set<String> textSet = Arrays.asList(Symbol.values()).stream().map(it->it.name()).collect(Collectors.toSet());
		List<String>  list =  Arrays.asList(Symbol.values()).stream().map(it->it.getText()).collect(Collectors.toList());
		
		List<String> stringList = Arrays.asList(Symbol.values()).stream().map(it -> it.getText())
				.collect(Collectors.toList());
		Set<Character> charSet = new HashSet<Character>();
		for (String it : stringList) {
			Set<Character> set = it.chars().mapToObj((i) -> Character.valueOf((char) i)).collect(Collectors.toSet());
			charSet.addAll(set);
		}
		
		// TODO Auto-generated method stub
//		Arrays.asList(Keyword.values()).forEach(e -> {
//			String text = e.name();
//			 System.out.println(text);
//		});
		
		Arrays.asList(charSet).forEach(e -> {
			// System.out.println(e);
		});
	    Map<String, TokenType> map = 	Arrays.asList(Keyword.values()).stream().collect(Collectors.toMap(Keyword::name, TokenType -> TokenType));
//		for(String key : map.keySet()) {
//			System.out.println(key  +" :  " +map.get(key).getClass().getSimpleName());
//		}
	//	System.out.println(Symbol.typeOf(";"));
		
		assert(1>2);
	}

}
