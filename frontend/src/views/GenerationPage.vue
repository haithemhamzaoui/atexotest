<script>
import GenerationForm from '@/components/GenerationForm.vue';
import Toast from '@/components/Toast.vue';
import apiService from "@/services/apiService";

export default {
  components: {
    GenerationForm,
    Toast,
  },
  data() {
    return {
      /**
       * The count of configurations retrieved from the API.
       */
      configurationsCount: 0,
    };
  },
  async created() {
    try {
      const response = await apiService.getConfigurations();
      // Set the configurations count based on the response
      this.configurationsCount = response.data.length;
      // If no configurations are found, alert the user and redirect to the config page
      if (this.configurationsCount === 0) {
        this.$refs.toast.show("Pas de configuration, veuillez en créer une avant de générer un ID unique.");
        this.$router.replace('/config');
      }
    } catch (error) {
      // Handle errors by setting configurations count to 0, alerting the user, and redirecting to the config page
      this.configurationsCount = 0;
      this.$refs.toast.show("Pas de configuration, veuillez en créer une avant de générer un ID unique.");
      this.$router.replace('/config');
    }
  }
};
</script>
<template>
  <div v-if="configurationsCount>0">
    <h2>Générer un ID unique</h2>
    <div id="generationPage">
      <GenerationForm/>
    </div>
  </div>
</template>
<style>
#generationPage {
  text-align: left;
  color: #2c3e50;
  width: 800px;
}
</style>