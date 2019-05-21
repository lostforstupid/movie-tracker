Vue.component('movie-view', {
  props: ['movie'],
  template: '#movie',
  computed: {
    getImageSource() {
      return 'data:image/jpeg;base64,' + this.movie.poster;
    },
    ifMovieIsWatched() {
      return this.movie.status === "WATCHED";
    }
  },
  methods: {
    removeMovieFromLibrary: function () {
      axios.delete(LIBRARY_URL + "/" + data.user.id
          + REMOVE_FROM_LIBRARY_URL + this.movie.id);
      window.location.reload();
    },
    markWatched: function () {
      axios.put(LIBRARY_URL + "/" + data.user.id
          + UPDATE_STATUS + this.movie.id + "?status=WATCHED");
      window.location.reload();
    },
    markToWatch: function () {
       axios.put(LIBRARY_URL + "/" + data.user.id
          + UPDATE_STATUS + this.movie.id + "?status=TO_WATCH");
       window.location.reload();
    }
  }
});

Vue.component('movie-list', {
  props: ['toWatch', 'watched'],
  template: '#movie-list',
  computed: {
    isToWatchEmpty() {
        return (this.toWatch === 'undefined' || this.toWatch.length <= 0);
    },
    isWatchedEmpty() {
        return (this.watched === 'undefined' || this.watched.length <= 0);
    },
    getProfilePicture() {
        return data.user.profilePictureUrl;
    }
  }
});

new Vue({
  el: '#app',
  template: '<movie-list :toWatch="toWatch" :watched="watched"/>',
  data: {
    toWatch: [],
    watched: []
  },
  created: function () {
    axios.get(LIBRARY_URL + "/" + data.user.id + "/to-watch").then(response => {
      let movieList = response.data;
      movieList.forEach(movie => this.toWatch.push(movie))
    });

    axios.get(LIBRARY_URL + "/" + data.user.id + "/watched").then(response => {
      let movieList = response.data;
      movieList.forEach(movie => this.watched.push(movie))
    });
  }
});