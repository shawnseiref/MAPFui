package backEnd.MapGenerators;

public abstract class AMapGenerator implements IMapGenerator {
    @Override
    public abstract Map generate(Object o);
}
