const BASE_URL = 'http://localhost:8080';

const MOVIES_URL = '/movies';
const LIBRARY_URL = '/user-movie';
const ADD_TO_LIBRARY_URL = "/add/";
const REMOVE_FROM_LIBRARY_URL = "/remove/";
const UPDATE_STATUS = "/update-status/";
const OPEN_EDIT_MOVIE_PAGE = "/edit/";

const instance = axios.create({
  baseURL: BASE_URL,
  timeout: 1000
});