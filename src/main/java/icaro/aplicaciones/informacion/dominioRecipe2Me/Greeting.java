package icaro.aplicaciones.informacion.dominioRecipe2Me;

public class Greeting {

    private String content;
    private Recipe recipe;
    private boolean tenedor = false;

    public Greeting(String content) {
        this.content = content;
    }

    public Greeting(String message, Recipe recipe, boolean valorar) {
		this.content = message;
		this.recipe = recipe;
		this.tenedor = valorar;
	}

	public String getContent() {
        return content;
    }
    
    public Recipe getRecipe() {
    	return recipe;
    }
    
    public void setRecipe(Recipe recipe) {
    	this.recipe = recipe;
    }

	public boolean isTenedor() {
		return tenedor;
	}

	public void setTenedor(boolean tenedor) {
		this.tenedor = tenedor;
	}

}
