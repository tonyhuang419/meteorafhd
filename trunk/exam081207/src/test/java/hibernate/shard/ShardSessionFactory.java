package hibernate.shard;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.cfg.Configuration;
import org.hibernate.shards.ShardId;
import org.hibernate.shards.ShardedConfiguration;
import org.hibernate.shards.cfg.ConfigurationToShardConfigurationAdapter;
import org.hibernate.shards.cfg.ShardConfiguration;
import org.hibernate.shards.loadbalance.RoundRobinShardLoadBalancer;
import org.hibernate.shards.strategy.ShardStrategy;
import org.hibernate.shards.strategy.ShardStrategyFactory;
import org.hibernate.shards.strategy.ShardStrategyImpl;
import org.hibernate.shards.strategy.access.SequentialShardAccessStrategy;
import org.hibernate.shards.strategy.access.ShardAccessStrategy;
import org.hibernate.shards.strategy.resolution.AllShardsShardResolutionStrategy;
import org.hibernate.shards.strategy.resolution.ShardResolutionStrategy;
import org.hibernate.shards.strategy.selection.RoundRobinShardSelectionStrategy;
import org.hibernate.shards.strategy.selection.ShardSelectionStrategy;

import com.exam.entity.ShardBook;


public class ShardSessionFactory {



	public static void main(String[] args) {
		ShardBook sb = new ShardBook();
		sb.setAuthorName("作者名称");
		sb.setCategory("category");
		sb.setEditior("editior");
		sb.setIsbn("isbn");
		sb.setPrice(new BigDecimal("1")); 
		sb.setPublisher("publisher");
		sb.setQuantityInStock(100L);
		sb.setTitle("title");
		sb.setYear(new Date());

		ShardBook sb2 = new ShardBook();
		sb2.setAuthorName("作者名称");
		sb2.setCategory("category");
		sb2.setEditior("editior");
		sb2.setIsbn("isbn");
		sb2.setPrice(new BigDecimal("1")); 
		sb2.setPublisher("publisher");
		sb2.setQuantityInStock(100L);
		sb2.setTitle("title");
		sb2.setYear(new Date());


		ShardSessionFactory ssf = new ShardSessionFactory();
		SessionFactory sf = ssf.createSessionFactory();
		Session session = sf.openSession();

		session.beginTransaction();
		session.save(sb);
		session.save(sb2);
		session.getTransaction().commit();

		session.close();
	}


	public SessionFactory createSessionFactory() {
		//		Configuration prototypeConfig = new Configuration().configure("shard0.hibernate.cfg.xml");
		//		prototypeConfig.addResource("weather.hbm.xml");
		//		List<ShardConfiguration> shardConfigs = new ArrayList<ShardConfiguration>();
		//		shardConfigs.add(buildShardConfig("shard0.hibernate.cfg.xml"));
		//		shardConfigs.add(buildShardConfig("shard1.hibernate.cfg.xml"));
		//		shardConfigs.add(buildShardConfig("shard2.hibernate.cfg.xml"));
		//		ShardStrategyFactory shardStrategyFactory = buildShardStrategyFactory();
		//		ShardedConfiguration shardedConfig = new ShardedConfiguration(
		//				prototypeConfig,
		//				shardConfigs,
		//				shardStrategyFactory);
		//		return shardedConfig.buildShardedSessionFactory();
		AnnotationConfiguration prototypeConfig = new AnnotationConfiguration().configure("shard0.hibernate.cfg.xml");
		prototypeConfig.addAnnotatedClass(ShardBook.class);
		List<ShardConfiguration> shardConfigs = new ArrayList<ShardConfiguration>();
		shardConfigs.add(buildShardConfig("shard0.hibernate.cfg.xml"));
		shardConfigs.add(buildShardConfig("shard1.hibernate.cfg.xml"));
		//		        shardConfigs.add(buildShardConfig("shard2.hibernate.cfg.xml"));
		ShardStrategyFactory shardStrategyFactory = buildShardStrategyFactory();
		ShardedConfiguration shardedConfig = new ShardedConfiguration(
				prototypeConfig,
				shardConfigs,
				shardStrategyFactory);
		return shardedConfig.buildShardedSessionFactory();

	}

	private ShardStrategyFactory buildShardStrategyFactory() {
		ShardStrategyFactory shardStrategyFactory = new ShardStrategyFactory() {
			public ShardStrategy newShardStrategy(List<ShardId> shardIds) {
				RoundRobinShardLoadBalancer loadBalancer = new RoundRobinShardLoadBalancer(shardIds);
				ShardSelectionStrategy pss = new RoundRobinShardSelectionStrategy(loadBalancer);
				ShardResolutionStrategy prs = new AllShardsShardResolutionStrategy(shardIds);
				ShardAccessStrategy pas = new SequentialShardAccessStrategy();
				return new ShardStrategyImpl(pss, prs, pas);
			}
		};
		return shardStrategyFactory;
	}


	private ShardConfiguration buildShardConfig(String configFile) {
		Configuration config = new Configuration().configure(configFile);
		return new ConfigurationToShardConfigurationAdapter(config);
	}

}
