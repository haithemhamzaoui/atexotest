<script>
import apiService from "@/services/apiService";
import Toast from './Toast.vue';

export default {
  components: {
    Toast,
  },
  data() {
    return {
      // Init configurations
      configurations: [
        {name: "nom", display: "Nom", prefix: "", suffix: "", length: "", configOrder: 1},
        {name: "prenom", display: "Prénom", prefix: "", suffix: "", length: "", configOrder: 2},
        {name: "dateOfBirth", display: "Année de Date Naissance", prefix: "", suffix: "", length: "", configOrder: 3},
        {name: "compteur", display: "Compteur", prefix: "", suffix: "", length: "", configOrder: 4, initValue: ""},
      ],
      savedConfigurations: 0,
    };
  },
  async created() {
    try {
      // Fetch existing configurations from the API
      const response = await apiService.getConfigurations();
      // show existing configurations, else show the init configurations
      if (response.data && response.data.length > 0) {
        this.configurations = response.data;
        this.savedConfigurations = response.data.length;
      }
    } catch (error) {
      console.error("Erreur lors de la récupération des configurations :", error);
    }
  },
  methods: {
    /**
     * Ensures the order of configuration fields is unique and valid.
     * @param {number} currentIndex - Index of the field being validated.
     */
    validateOrder(currentIndex) {
      const currentOrder = this.configurations[currentIndex].configOrder;

      // Check for duplicate orders
      const usedOrders = new Set(
          this.configurations
              .filter((_, index) => index !== currentIndex)
              .map(field => field.configOrder)
      );

      if (usedOrders.has(currentOrder)) {
        this.$refs.toast.show("Chaque champ doit avoir un ordre unique !");
        this.configurations[currentIndex].configOrder = null;
        return;
      }

      // Check if order is within the valid range
      if (currentOrder < 1 || currentOrder > this.configurations.length) {
        this.$refs.toast.show(`L'ordre doit être compris entre 1 et ${this.configurations.length} !`);
        this.configurations[currentIndex].configOrder = null;
      }

    },
    /**
     * Save the current configurations to the database via the API.
     */
    async saveConfiguration() {
      try {
        await apiService.configureCriteria(this.configurations);
        this.savedConfigurations = this.configurations.length;
        this.$refs.toast.show("Configuration enregistrée avec succès !");
      } catch (error) {
        console.error("Erreur lors de l'enregistrement :", error);
        this.$refs.toast.show("Échec de l'enregistrement des configurations.");
      }
    },
    /**
     * Reset all configurations to their default state.
     */
    async resetConfigurations() {
      try {
        await apiService.resetConfigurations();
        this.savedConfigurations = 0;
        this.configurations = [
          { name: "nom", display: "Nom", prefix: "", suffix: "", length: "", configOrder: 1 },
          { name: "prenom", display: "Prénom", prefix: "", suffix: "", length: "", configOrder: 2 },
          { name: "dateOfBirth", display: "Année de Date Naissance", prefix: "", suffix: "", length: "", configOrder: 3 },
          { name: "compteur", display: "Compteur", prefix: "", suffix: "", length: "", configOrder: 4, initValue: "" },
        ];
        this.$refs.toast.show("Configurations réinitialisées avec succès !");
      } catch (error) {
        console.error("Erreur lors de la réinitialisation :", error);
        this.$refs.toast.show("Impossible de réinitialiser les configurations.");
      }
    },
  },
};
</script>

<template>
  <div>
    <Toast ref="toast" />
    <form @submit.prevent="saveConfiguration" @reset.prevent="resetConfigurations">
      <div v-for="(field, index) in configurations" :key="field.name">
        <h4>{{ field.display }} :</h4>
        <input
            v-model="field.prefix"
            class="input"
            placeholder="Préfixe"
        />
        <input
            v-model="field.suffix"
            class="input"
            placeholder="Suffixe"
        />
        <input
            v-model.number="field.length"
            class="input"
            min="0"
            placeholder="Longueur"
            type="number"
        />
        <label>Ordre :</label>
        <input
            v-model.number="field.configOrder"
            :max="configurations.length"
            class="inputOrder"
            min="1"
            required
            type="number"
            @change="validateOrder(index)"
        />
        <input
            v-if="field.name === 'compteur'"
            v-model.number="field.initValue"
            class="input"
            min="0"
            placeholder="Valeur initiale"
            required
            type="number"
        />
      </div>
      <div class="buttons-block">
        <button v-if="savedConfigurations > 0" type="reset" class="btn-reset">Réinitialiser</button>
        <button type="submit" class="btn-submit">Enregistrer</button>
      </div>
    </form>
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

.input, .inputOrder {
  margin: 5px;
  padding: 8px;
  border: 1px solid #ccc;
  border-radius: 4px;
}

.input:focus, .inputOrder:focus {
  border-color: #007BFF;
}

.btn-submit {
  padding: 10px 20px;
  border: none;
  border-radius: 4px;
  cursor: pointer;
}

.btn-submit {
  background-color: #007BFF;
  color: white;
}

.btn-reset {
  background-color: #FF0000;
  color: white;
}

button:hover {
  opacity: 0.9;
}

.buttons-block {
  display: flex;
  justify-content: space-between;
}

h4 {
  margin-bottom: 5px;
  color: #333;
}
</style>
