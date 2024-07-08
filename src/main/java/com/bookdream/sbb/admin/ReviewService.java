package com.bookdream.sbb.admin;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bookdream.sbb.DataNotFoundException;
import com.bookdream.sbb.user.SiteUser;
import com.bookdream.sbb.user.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReviewService {
	private final UserRepository userRepository;
}