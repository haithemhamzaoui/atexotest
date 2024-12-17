<script>
import apiService from '@/services/apiService';

export default {
  data() {
    return {
      inscritData: {
        name: '',
        firstName: '',
        dateOfBirth: '',
      },
      generatedNumber: '',
    };
  },
  methods: {
    async generateNumber() {
      try {
        const response = await apiService.generateNumber(this.inscritData);
        this.generatedNumber = response.data;
      } catch (error) {
        console.error('Error generating number:', error);
      }
    },
  },
};
</script>
<template>
  <div>
    <form @submit.prevent="generateNumber">
      <label>Nom:</label>
      <input v-model="inscritData.name" required placeholder="Entrez votre nom"/>

      <label>Prénom:</label>
      <input v-model="inscritData.firstName" required placeholder="Entrez votre prénom"/>

      <label>Date de naissance:</label>
      <input v-model="inscritData.dateOfBirth" required type="date" placeholder="Entrez votre date de naissance"/>

      <button type="submit">Générer</button>
    </form>

    <div v-if="generatedNumber">
      <h3>Numéro généré:</h3>
      <p>{{ generatedNumber }}</p>
    </div>
  </div>
</template>
<style scoped>
form {
  display: flex;
  flex-direction: column;
  gap: 10px;
}
</style>