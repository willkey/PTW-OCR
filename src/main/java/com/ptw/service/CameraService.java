package com.ptw.service;

import com.ptw.tools.PTWResult;

public interface CameraService {
	public PTWResult idCard_front();
	public PTWResult idCard_behind();
	public PTWResult passport();
}
