import { db } from '../config/firebase.js';



class CarRepository{
    async list(){
        try{
            const Cars = await db.collection('cars').get();
            return Cars;
        }catch(error){
            console.error('Error getting Cars', error);
        }
    }

    async getById(id){
        try{
            return await db.collection('cars').doc(id).get();
        }catch(error){
            console.error('Error getting Car by id', error);
        }
    }

    async create(Car){
        try{
            const response = await db.collection('cars').add(Car);
            return response;
        }catch(error){
            console.error('Error creating Car', error);
        }
    }

    async update(id, Car){
        try{
            await db.collection('cars').doc(id).update(Car);
            return true;
        }catch(error){
            console.error('Error updating Car', error);
        }
    }

    async delete(id){
        try{
            await db.collection('cars').doc(id).delete();
            return true;
        }catch(error){
            console.error('Error deleting Car', error);
        }
    }
}


export default new CarRepository();