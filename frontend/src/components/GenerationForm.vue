<script>
import apiService from '@/services/apiService';

export default {
  data() {
    return {
      inscritData: {
        nom: '',
        prenom: '',
        dateOfBirth: '',
      },
      generatedNumber: '',
      generations: [],
    };
  },
  async created() {
    try {
      // Fetch existing generations from the API
      const response = await apiService.getGenerations();
      // show existing generations, else show the init configurations
      if (response.data && response.data.length > 0) {
        this.generations = response.data;
      }
    } catch (error) {
      console.error("Erreur lors de la récupération des générations :", error);
    }
  },
  methods: {
    /**
     * Generates a number based on the user's input data.
     * Converts the date of birth to a year before sending it to the API.
     * Updates the generatedNumber with the response from the API.
     * Logs an error if the API call fails.
     */
    async generateId() {
      this.inscritData.dateOfBirth = new Date(this.inscritData.dateOfBirth).getFullYear();
      try {
        const response = await apiService.generateId(this.inscritData);
        this.generatedNumber = response.data;
        // Fetch the updated generations list
        const generationsResponse = await apiService.getGenerations();
        this.generations = generationsResponse.data;
      } catch (error) {
        console.error('Error generating number:', error);
      }
    },
  },
};
</script>
<template>
  <div class="container">
    <div>
      <form @submit.prevent="generateId">
        <h4>Nom:</h4>
        <input v-model="inscritData.nom"
               class="input" placeholder="Entrez votre nom" required/>

        <h4>Prénom:</h4>
        <input v-model="inscritData.prenom"
               class="input" placeholder="Entrez votre prénom" required/>

        <h4>Date de naissance:</h4>
        <input v-model="inscritData.dateOfBirth"
               class="input" placeholder="Entrez votre date de naissance" required type="date"/>

        <button type="submit">Générer</button>
      </form>

      <div v-if="generatedNumber">
        <h3>Numéro généré:</h3>
        <p>{{ generatedNumber }}</p>
      </div>
    </div>
    <div v-if="generations.length > 0" class="generationList">
      <h3>Anciens générations:</h3>
      <div v-for="(generation, index) in generations" :key="index">
        <p>{{ generation.generatedValue }}</p>
      </div>
    </div>
  </div>
</template>
<style scoped>
form {
  display: flex;
  flex-direction: column;
  gap: 10px;
  padding: 20px;
  background-color: #f9f9f9;
  border-radius: 8px;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
}

.container {
  display: flex;
  flex-direction: row;
}

.generationList {
  margin-left: 30px;
}

input::-webkit-outer-spin-button, input::-webkit-inner-spin-button {
  -webkit-appearance: none;
  margin: 0;
}

.input {
  padding: 8px;
  border: 1px solid #ccc;
  border-radius: 2px;
  transition: border-color 0.3s;
}

.input:focus {
  border-color: #007BFF;
}

button {
  padding: 10px 20px;
  border: none;
  border-radius: 4px;
  background-color: #007BFF;
  color: white;
  cursor: pointer;
  transition: background-color 0.3s;
}

button:hover {
  background-color: #0056b3;
}

h4 {
  margin-bottom: 5px;
  color: #333;
}
</style>