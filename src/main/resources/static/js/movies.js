Vue.component('movie-view', {
  props: ['movie'],
  template: '#movie-view',
  methods: {
    deleteMovie: function () {
      axios.delete(MOVIES_URL + "/" + this.movie.id);
      window.location.reload();
    },
    addToUserLibrary: function () {
      axios.post(LIBRARY_URL + "/" + data.user.id
          + ADD_TO_LIBRARY_URL + this.movie.id);
    }
  },
  computed: {
    getImageSource() {
      return 'data:image/jpeg;base64,' + this.movie.poster;
    },
    ifMovieInLibrary() {
      return !(this.movie.inUserLibrary);
    }
  }
});

Vue.component('movie-list', {
  props: ['movies'],
  template: '#movie-list'
});

new Vue({
  el: '#app',
  template: '<movie-list :movies="movies"/>',
  data: {
    movies: []
  },
  created: function () {
    const params = {userId: data.user.id};

    axios.get(MOVIES_URL, { params }).then(response => {
      let movieList = response.data;
      movieList.forEach(movie => this.movies.push(movie))
    })
  }
});