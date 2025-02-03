import os
import json

# Function to generate the blockstate JSON data for bar counters
def get_blockstate(wood_type):
    return {
        "variants": {
            # Single counter variants
            "facing=north,type=single": { "model": f"furnies:block/bar_counter/{wood_type}" },
            "facing=east,type=single":  { "model": f"furnies:block/bar_counter/{wood_type}", "y": 90 },
            "facing=south,type=single": { "model": f"furnies:block/bar_counter/{wood_type}", "y": 180 },
            "facing=west,type=single":  { "model": f"furnies:block/bar_counter/{wood_type}", "y": 270 },

            # Inner counter variants
            "facing=north,type=inner_left": { "model": f"furnies:block/bar_counter/{wood_type}_inner" },
            "facing=east,type=inner_left":  { "model": f"furnies:block/bar_counter/{wood_type}_inner", "y": 90 },
            "facing=south,type=inner_left": { "model": f"furnies:block/bar_counter/{wood_type}_inner", "y": 180 },
            "facing=west,type=inner_left":  { "model": f"furnies:block/bar_counter/{wood_type}_inner", "y": 270 },

            "facing=north,type=inner_right": { "model": f"furnies:block/bar_counter/{wood_type}_inner", "y": 90 },
            "facing=east,type=inner_right":  { "model": f"furnies:block/bar_counter/{wood_type}_inner", "y": 180 },
            "facing=south,type=inner_right": { "model": f"furnies:block/bar_counter/{wood_type}_inner", "y": 270 },
            "facing=west,type=inner_right":  { "model": f"furnies:block/bar_counter/{wood_type}_inner" },

            # Outer counter variants
            "facing=north,type=outer_left": { "model": f"furnies:block/bar_counter/{wood_type}_outer" },
            "facing=east,type=outer_left":  { "model": f"furnies:block/bar_counter/{wood_type}_outer", "y": 90 },
            "facing=south,type=outer_left": { "model": f"furnies:block/bar_counter/{wood_type}_outer", "y": 180 },
            "facing=west,type=outer_left":  { "model": f"furnies:block/bar_counter/{wood_type}_outer", "y": 270 },

            "facing=north,type=outer_right": { "model": f"furnies:block/bar_counter/{wood_type}_outer", "y": 90 },
            "facing=east,type=outer_right":  { "model": f"furnies:block/bar_counter/{wood_type}_outer", "y": 180 },
            "facing=south,type=outer_right": { "model": f"furnies:block/bar_counter/{wood_type}_outer", "y": 270 },
            "facing=west,type=outer_right":  { "model": f"furnies:block/bar_counter/{wood_type}_outer" }
        }
    }

# Function to generate the "single" block model JSON data
def get_block_model(wood_type):
    return {
        "parent": "furnies:block/template/bar_counter",
        "textures": {
            "1": f"furnies:block/bar_counter/{wood_type}"
        }
    }

# Function to generate the inner block model JSON data
def get_inner_model(wood_type):
    return {
        "parent": "furnies:block/template/bar_counter_inner",
        "textures": {
            "1": f"furnies:block/bar_counter/{wood_type}"
        }
    }

# Function to generate the outer block model JSON data
def get_outer_model(wood_type):
    return {
        "parent": "furnies:block/template/bar_counter_outer",
        "textures": {
            "1": f"furnies:block/bar_counter/{wood_type}"
        }
    }

# Function to generate the item model JSON data
def get_item_model(wood_type):
    return {
        "parent": f"furnies:block/bar_counter/{wood_type}"
    }

# Utility function to save a JSON file
def save_json_file(path, data):
    os.makedirs(os.path.dirname(path), exist_ok=True)
    with open(path, "w", encoding="utf-8") as f:
        json.dump(data, f, indent=4)

# Main function to generate all files for each wood type
def generate_files():
    # Read wood types from woods.json (should be a JSON array, e.g., ["acacia", "birch", ...])
    script_dir = os.path.dirname(os.path.abspath(__file__))
    woods_file = os.path.join(script_dir, "woods.json")
    
    with open(woods_file, "r", encoding="utf-8") as f:
        woods = json.load(f)
    
    # Base directory for generated files
    base_dir = os.path.join(script_dir, "furnies")
    
    for wood in woods:
        # Generate blockstate file: furnies/blockstates/<wood>_bar_counter.json
        blockstate_path = os.path.join(base_dir, "blockstates", f"{wood}_bar_counter.json")
        save_json_file(blockstate_path, get_blockstate(wood))
        
        # Generate block models in furnies/models/block/bar_counter/
        # Single model: <wood>.json
        block_model_path = os.path.join(base_dir, "models", "block", "bar_counter", f"{wood}.json")
        save_json_file(block_model_path, get_block_model(wood))
        
        # Inner model: <wood>_inner.json
        inner_model_path = os.path.join(base_dir, "models", "block", "bar_counter", f"{wood}_inner.json")
        save_json_file(inner_model_path, get_inner_model(wood))
        
        # Outer model: <wood>_outer.json
        outer_model_path = os.path.join(base_dir, "models", "block", "bar_counter", f"{wood}_outer.json")
        save_json_file(outer_model_path, get_outer_model(wood))
        
        # Generate item model: furnies/models/item/<wood>_bar_counter.json
        item_model_path = os.path.join(base_dir, "models", "item", f"{wood}_bar_counter.json")
        save_json_file(item_model_path, get_item_model(wood))

if __name__ == "__main__":
    generate_files()
