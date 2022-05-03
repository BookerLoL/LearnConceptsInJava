package designpatterns.abstractfactory;

public class PetAnimalFactory implements AnimalFactory {

	@Override
	public Dog createDog() {
		return new PetDog();
	}

	@Override
	public Cat createCat() {
		return new PetCat();
	}

}
