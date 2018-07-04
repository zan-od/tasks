package com.zan.tasks.service;

import org.springframework.stereotype.Component;

@Component
public class DurationFormatterService {
	public String formatDuration(Integer duration) {
		StringBuilder result = new StringBuilder();
		int seconds = duration;
		
		if (seconds < 0) {
			seconds = -seconds;
			result.append("-");
		}
		
		if (seconds < 60){
			result.append(seconds).append("s");
		} else {
			int minutes = (int) seconds/60;
			seconds -= minutes*60;
			
			int hours = (int) minutes/60;
			minutes -= hours*60;
			
			if (hours > 0) {
				result.append(hours).append("h ");
			}
			
			result.append(String.format("%02d", minutes)).append("m");
		}
		
		return result.toString();
	}
}
