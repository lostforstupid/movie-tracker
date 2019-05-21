new Vue({
  el: "#add-form",
  data: {
    name: '',
    description: '',
    poster: null
  },
  methods: {
    processForm: function() {
      let formData = new FormData();
      formData.append('name', this.name);
      formData.append('description', this.description);
      formData.append('poster', this.poster);

      instance.put(MOVIES_URL + "/" + data.id, formData, {
        headers: {
          'Content-Type': 'multipart/form-data'
        }
      })
      .then(function (response) {
        console.log(response);
        window.location.reload();
      })
      .catch(function (error) {
        console.log(error);
      });
    },
    handleFileUpload: function (event) {
      this.poster = event.target.files[0];
    }
  }
});

