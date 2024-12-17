<script>
import apiService from "@/services/apiService";

export default {
  data() {
    return {
      // Init configurations
      configurations: [
        {name: "nom", prefixLength: 0, suffixLength: 0, order: 1},
        {name: "prenom", prefixLength: 0, suffixLength: 0, order: 2},
        {name: "dateNaissanceAnnee", prefixLength: 0, suffixLength: 0, order: 3},
        {name: "compteur", initValue: 0, longeur: 1, order: 4},
      ],
    };
  },
  methods: {
    validateOrder(currentIndex) {
      const currentOrder = this.configurations[currentIndex].order;

      const usedOrders = new Set();
      this.configurations.forEach((field, index) => {
        if (index !== currentIndex) {
          usedOrders.add(field.order);
        }
      });
      // Verify uniqueness of order
      if (usedOrders.has(currentOrder)) {
        alert("L'ordre doit être unique pour chaque champ !");
        this.configurations[currentIndex].order = null;
      }

      // Verify order is between 1 and number of configurations fields
      if (currentOrder < 1 || currentOrder > this.configurations.length) {
        alert("L'ordre doit être compris entre 1 et " + this.configurations.length + " !");
      }
      // If order is not unique, find an available order in [1, configurations.length]
      let availableOrder = 1;
      while (this.configurations.some(field => field.order === availableOrder)) {
        availableOrder++;
      }
      this.configurations[currentIndex].order = availableOrder;
    },
    async saveConfiguration() {
      try {
        const response = await apiService.configureCriteria(this.configurations);
        console.log("Configuration sauvegardée :", response.data);
        alert("Configuration enregistrée avec succès !");
      } catch (error) {
        console.error("Erreur lors de l'enregistrement :", error);
        alert("Erreur lors de l'enregistrement de la configuration.");
      }
    },
  },
};
</script>
<template>
  <div>
    <form @submit.prevent="saveConfiguration">
      <div v-for="(field, index) in configurations" :key="field.name">
        <!-- other fields (nom, prenom, dateNaissanceAnnee) have the same properties -->
        <div v-if="field.name !== 'compteur'">
          <h3>{{ field.name.toUpperCase() }}</h3>
          <label>Préfixe :</label>
          <input
              type="number"
              v-model.number="field.prefixLength"
              min="0"
              required
          />
          &emsp;
          <label>Suffixe :</label>
          <input
              type="number"
              v-model.number="field.suffixLength"
              min="0"
              required
          />
          &emsp;
          <label>Ordre :</label>
          <input
              type="number"
              v-model.number="field.order"
              min="1"
              :max="configurations.length"
              required
              @change="validateOrder(index)"
          />
        </div>
        <div v-else>
          <h3>{{ field.name.toUpperCase() }}</h3>
          <label>Valeur :</label>
          <input
              type="number"
              v-model.number="field.initValue"
              min="0"
              required
          />
          &emsp;
          <label>Longueur :</label>
          <input
              type="number"
              v-model.number="field.longeur"
              min="1"
              required
          />
          &emsp;
          <label>Ordre :</label>
          <input
              type="number"
              v-model.number="field.order"
              min="1"
              :max="configurations.length"
              required
              @change="validateOrder(index)"
          />
        </div>
      </div>

      <button type="submit">Enregistrer la Configuration</button>
    </form>
  </div>
</template>
<style scoped>
form {
  display: flex;
  flex-direction: column;
  gap: 10px;
}
</style>
