import express from 'express';

//Controllers
import CarController from '../controllers/CarController.js';

//Middlewares
import verifyToken from '../middlewares/verifyToken.js';


class CarRouter {
    constructor() {
        this.router = express.Router();
        this.CarController = CarController;
        this.setupRoutes();
    }

    setupRoutes() {
        //INDEX
        this.router.get('/cars', verifyToken, (req, res) => this.CarController.index(req, res));

        //CREATE
        this.router.get('/cars/create', verifyToken, (req, res) => {
            return res.render('car/create', { layout: 'main', title: 'Cadastrar Grand Prix' });
        });
        this.router.post('/cars/store', verifyToken, (req, res) => this.CarController.store(req, res));

        //UPDATE
        this.router.get('/cars/edit/:id', verifyToken, (req, res) => this.CarController.edit(req, res));
        this.router.post('/cars/update/:id', verifyToken, (req, res) => this.CarController.update(req, res));

        //DELETE
        this.router.delete('/cars/delete/:id', verifyToken, (req, res) => this.CarController.delete(req, res));
    }

    getRouter(){
        return this.router
    }
}

export default new CarRouter();