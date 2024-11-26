import { db } from '../config/firebase.js';



class GrandPrixRepository{
    async list(){
        try{
            const GrandPrixs = await db.collection('GrandPrixs').get();
            return GrandPrixs;
        }catch(error){
            console.error('Error getting GrandPrixs', error);
        }
    }

    async getById(id){
        try{
            return await db.collection('GrandPrixs').doc(id).get();
        }catch(error){
            console.error('Error getting GrandPrix by id', error);
        }
    }

    async create(GrandPrix){
        try{
            const response = await db.collection('GrandPrixs').add(GrandPrix);
            return response;
        }catch(error){
            console.error('Error creating GrandPrix', error);
        }
    }

    async update(id, GrandPrix){
        try{
            await db.collection('GrandPrixs').doc(id).update(GrandPrix);
            return true;
        }catch(error){
            console.error('Error updating GrandPrix', error);
        }
    }

    async delete(id){
        try{
            await db.collection('GrandPrixs').doc(id).delete();
            return true;
        }catch(error){
            console.error('Error deleting GrandPrix', error);
        }
    }
}


export default new GrandPrixRepository();