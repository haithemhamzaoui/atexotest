<script>
import ConfigurationForm from '@/components/ConfigurationForm.vue';

export default {
  components: {
    ConfigurationForm,
  },
};
</script>

<template>
  <div>
    <h2>Configurer les critères de numérotation</h2>
    <div id="configurationPage">
      <ConfigurationForm/>
    </div>
  </div>
</template>

<style scoped>
#configurationPage {
  text-align: left;
  color: #2c3e50;
  margin-top: 60px;
  width: 700px;
}
</style>