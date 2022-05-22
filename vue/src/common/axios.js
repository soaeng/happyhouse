import axios from 'axios';

export default axios.create({
  baseURL: 'http://localhost:8080',
  headers: {
    "Content-Type": "application/json",
  },
  // sessionId 고정
  withCredentials: true // default: false, false인 경우 sessionId를 매번 신규로 발급
});