const BASE_URL = 'http://localhost:8080';

const MOVIES_URL = '/movies';
const LIBRARY_URL = '/user-movie';
const ADD_TO_LIBRARY_URL = "/add/";
const REMOVE_FROM_LIBRARY_URL = "/remove/";
const CHECK_IF_EXISTS_URL = "/exists/";

const instance = axios.create({
  baseURL: BASE_URL,
  timeout: 1000
});