package com.zan.tasks.service;

import java.io.IOException;
import java.text.ParseException;

import com.zan.tasks.model.Board;

public interface Web2PyLoaderService {
	void load(String baseUrl, String username, String password, Board board) throws IOException, ParseException;
}
