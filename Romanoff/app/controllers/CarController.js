import CarRepository from '../repositories/CarRepository.js';


class CarController {
    async index(req, res) {
        try{
            const cars = await CarRepository.list()
            .then((cars) => {
                const carsData = cars.docs.map((car) => {
                    return {
                        id: car.id,
                        ...car.data()
                    }
                });
    
                return carsData;
            });
    
            console.log('Carros:', cars);
            return res.render('car/index', { layout: 'main', title: 'Carros', cars: cars });
        }catch(error){
            console.error('Erro ao consultar Carros:', error);
            res.render("errors/error", {layout: "guest", codError: "500", textError: 'Erro no Servidor!'});
        }
    }

    async store(req, res) {
        try{
            const { nome, modelo, ano, marca } = req.body;
            const Car = await CarRepository.create({ nome, modelo, ano, marca });
    
            if (Car) {
                req.session.success = 'Carro Cadastrada com Sucesso!';
                return res.redirect('/cars');
            } else {
                throw new Error('Erro ao Cadastrar Carro!');
            }
        }catch(error){
            console.error('Erro ao cadastrar Carro:', error);
            req.session.error = 'Erro ao Cadastrar Carro!';
            return res.redirect('/cars/create');
        }
    }

    async edit(req, res) {
        try{
            const car = await CarRepository.getById(req.params.id).then((car) => {
                return {
                    id: car.id,
                    ...car.data()
                }
            });
        
            return res.render('car/edit', { layout: 'main', title: 'Editar Carro', car: car });
        }
        catch(error){
            console.error('Erro ao Visualizar Carro:', error);
            req.session.error = 'Erro ao Visualizar Carro!';
            return res.redirect('/cars');
        }
    }

    async update(req, res) {
        try{
            const id = req.params.id;
            const { nome, modelo, ano, marca } = req.body;
            const Car = await CarRepository.update(id, { nome, modelo, ano, marca });
    
            if (Car) {
                req.session.success = 'Carro Atualizada com Sucesso!';
                return res.redirect('/cars');
            } else {
                throw new Error('Erro ao Atualizar Carro!');
            }
        }
        catch(error){
            console.error('Erro ao atualizar Carro:', error);
            req.session.error = 'Erro ao Atualizar Carro!';
            return res.redirect('/cars/edit/' + req.params.id);
        }
    }

    async delete(req, res) {
        try{
            const Car = await CarRepository.delete(req.params.id);

            if (Car) {
                req.session.success = 'Carro Deletada com Sucesso!';
                return res.redirect('/cars');
            } else {
                throw new Error('Erro ao Deletar Carro!');
            }
        }
        catch(error){
            console.error('Erro ao Deletar Carro:', error);
            req.session.error = 'Erro ao Deletar Carro!';
            return res.redirect('/cars');
        }
    }
}


export default new CarController();