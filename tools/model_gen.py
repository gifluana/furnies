import os
import json
import itertools

def ensure_file(filename, content):
    """If filename does not exist, create it with the given JSON content."""
    if not os.path.exists(filename):
        with open(filename, 'w') as f:
            json.dump(content, f, indent=4)
        print(f"Created sample file: {filename}")

def load_json_file(filename):
    with open(filename, 'r') as f:
        return json.load(f)

def get_variant_options(variant_def):
    """
    Returns all possible values for a variant.
    For booleans, returns [False, True].
    For string/int types, expects a "values" list in the variant definition.
    """
    if variant_def["type"] == "boolean":
        return [False, True]
    else:
        return variant_def.get("values", [])

def compute_suffix(combination, variants_definitions):
    """
    Given a tuple (or empty tuple) of (variant_name, value) pairs,
    return the suffix string based on the variant definitions.
    For example, if the variant "open" (default false) is True then append
    the corresponding suffix (e.g. "_open"); if the value equals the default,
    nothing is added.
    """
    suffix = ""
    for variant_name, value in combination:
        variant_def = variants_definitions[variant_name]
        default = variant_def.get("default")
        if value != default:
            # For booleans, convert to lowercase string ("true" or "false")
            key = str(value).lower() if isinstance(value, bool) else str(value)
            part = variant_def.get("suffixes", {}).get(key, "")
            suffix += part
    return suffix

def main():
    # Ensure sample definition files exist:
    ensure_file("woods.json", [
        "acacia", "bamboo", "birch", "cherry", "crimson", 
        "dark_oak", "jungle", "mangrove", "oak", "spruce", "warped"
    ])
    ensure_file("colors.json", [
        "red", "orange", "yellow", "lime", "green", "cyan", "light_blue", 
        "blue", "magenta", "purple", "pink", "white", "light_gray", "gray", 
        "black", "brown"
    ])
    # Sample variants.json – you can modify or extend these definitions:
    sample_variants = {
        "open": {
            "type": "boolean",
            "default": False,
            "suffixes": {
                "true": "_open",
                "false": ""
            }
        },
        "hinge": {
            "type": "string",
            "default": "left",
            "values": ["left", "right"],
            "suffixes": {
                "left": "",
                "right": "_right"
            }
        }
    }
    ensure_file("variants.json", sample_variants)

    # 1. Ask if the datagen is for wood or colored
    mod_category = input("Is this datagen for wood or colored? (wood/colored): ").strip().lower()
    if mod_category == "wood":
        names = load_json_file("woods.json")
    elif mod_category == "colored":
        names = load_json_file("colors.json")
    else:
        print("Invalid option. Exiting.")
        return

    # 2. Ask for the block type name (ex: chair, table, kitchen_sink)
    block_type = input("Enter block type (e.g. chair, table, kitchen_sink): ").strip()

    # 3. Ask for the blockstate type: normal, rotational, or mirrored
    state_type = input("Is the block normal, rotational, or mirrored? (normal/rotational/mirrored): ").strip().lower()
    if state_type not in ["normal", "rotational", "mirrored"]:
        print("Invalid block state type. Exiting.")
        return

    # 4. Ask if any extra variant properties are desired – do it for all state types.
    variants_chosen = []
    variants_definitions = {}
    add_variants = input("Do you want to add extra variants? (y/n): ").strip().lower()
    if add_variants == "y":
        variants_definitions = load_json_file("variants.json")
        print("Available variants: " + ", ".join(variants_definitions.keys()))
        while True:
            variant_name = input("Enter variant name (or type 'done' to finish): ").strip()
            if variant_name.lower() == "done":
                break
            if variant_name not in variants_definitions:
                print("Variant not found. Please choose from available variants.")
            else:
                if variant_name in variants_chosen:
                    print("Variant already chosen.")
                else:
                    variants_chosen.append(variant_name)
    # Build a list of all combinations for the chosen extra variants.
    if variants_chosen:
        variant_options_list = []
        for variant_name in variants_chosen:
            variant_def = variants_definitions[variant_name]
            options = get_variant_options(variant_def)
            # Each option is stored as a (variant_name, option_value) pair.
            variant_options_list.append([(variant_name, opt) for opt in options])
        # Cartesian product of all variant options:
        variant_combinations = list(itertools.product(*variant_options_list))
    else:
        variant_combinations = [()]  # one combination: an empty tuple

    # 5. Prepare the output directories:
    blockstates_dir = os.path.join("furnies", "blockstates")
    os.makedirs(blockstates_dir, exist_ok=True)
    block_models_dir = os.path.join("furnies", "models", "block", block_type)
    os.makedirs(block_models_dir, exist_ok=True)
    item_models_dir = os.path.join("furnies", "models", "item")
    os.makedirs(item_models_dir, exist_ok=True)

    # 6. For each wood/color name, generate the files.
    for name in names:
        # Initialize blockstate and a set to hold unique model file suffixes.
        blockstate = {"variants": {}}
        model_suffixes = set()

        if state_type == "rotational":
            # Rotational: keys built with facing and extra variant properties.
            facings = [("north", 0), ("east", 90), ("south", 180), ("west", 270)]
            for facing, rotation in facings:
                for combination in variant_combinations:
                    # Build extra variant part (if any)
                    if combination:
                        assignments = []
                        for var, val in combination:
                            val_str = str(val).lower() if isinstance(val, bool) else str(val)
                            assignments.append(f"{var}={val_str}")
                        variant_str = "," + ",".join(assignments)
                    else:
                        variant_str = ""
                    key = f"facing={facing}{variant_str}"
                    extra_suffix = compute_suffix(combination, variants_definitions) if variants_chosen else ""
                    model_suffixes.add(extra_suffix)
                    entry = {"model": f"furnies:block/{block_type}/{name}{extra_suffix}"}
                    if rotation != 0:
                        entry["y"] = rotation
                    blockstate["variants"][key] = entry

        elif state_type == "normal":
            # Normal: keys without facing; add extra variant properties if provided.
            for combination in variant_combinations:
                if combination:
                    assignments = []
                    for var, val in combination:
                        val_str = str(val).lower() if isinstance(val, bool) else str(val)
                        assignments.append(f"{var}={val_str}")
                    key = ",".join(assignments)
                else:
                    key = ""
                extra_suffix = compute_suffix(combination, variants_definitions) if variants_chosen else ""
                model_suffixes.add(extra_suffix)
                blockstate["variants"][key] = {"model": f"furnies:block/{block_type}/{name}{extra_suffix}"}

        elif state_type == "mirrored":
            # Mirrored: keys include fixed properties (facing & type) plus any extra variants.
            facings = [("north", None), ("east", None), ("south", None), ("west", None)]
            mirrored_types = ["single", "inner_left", "inner_right", "outer_left", "outer_right"]
            for facing, _ in facings:
                for mtype in mirrored_types:
                    for combination in variant_combinations:
                        # Build key with fixed properties...
                        base = f"facing={facing},type={mtype}"
                        # ...and extra variant properties (if any)
                        if combination:
                            assignments = []
                            for var, val in combination:
                                val_str = str(val).lower() if isinstance(val, bool) else str(val)
                                assignments.append(f"{var}={val_str}")
                            extra_str = "," + ",".join(assignments)
                        else:
                            extra_str = ""
                        key = base + extra_str
                        # Compute mirrored suffix based on fixed "type"
                        if mtype == "single":
                            mirrored_suffix = ""
                        elif mtype.startswith("inner"):
                            mirrored_suffix = "_inner"
                        elif mtype.startswith("outer"):
                            mirrored_suffix = "_outer"
                        else:
                            mirrored_suffix = ""
                        # And compute extra variant suffix if any
                        extra_suffix = compute_suffix(combination, variants_definitions) if variants_chosen else ""
                        final_suffix = mirrored_suffix + extra_suffix
                        model_suffixes.add(final_suffix)
                        # Determine rotation mapping based on mirrored type:
                        if mtype.endswith("right"):
                            rot_mapping = {"north": 90, "east": 180, "south": 270, "west": 0}
                        else:
                            rot_mapping = {"north": 0, "east": 90, "south": 180, "west": 270}
                        rotation = rot_mapping[facing]
                        entry = {"model": f"furnies:block/{block_type}/{name}{final_suffix}"}
                        if rotation != 0:
                            entry["y"] = rotation
                        blockstate["variants"][key] = entry

        # Write the blockstate JSON file.
        blockstate_filename = os.path.join(blockstates_dir, f"{name}_{block_type}.json")
        with open(blockstate_filename, 'w') as f:
            json.dump(blockstate, f, indent=4)
        print(f"Generated blockstate: {blockstate_filename}")

        # --- Generate block model files ---
        # For each unique model file suffix collected, create a block model file.
        for suffix in model_suffixes:
            if suffix == "":
                parent = f"furnies:block/template/{block_type}"
            else:
                parent = f"furnies:block/template/{block_type}{suffix}"
            model_data = {
                "parent": parent,
                "textures": {
                    "0": f"furnies:block/{block_type}/{name}"
                }
            }
            model_filename = os.path.join(block_models_dir, f"{name}{suffix}.json")
            with open(model_filename, 'w') as f:
                json.dump(model_data, f, indent=4)
            print(f"Generated block model: {model_filename}")

        # --- Generate the item model file ---
        # The item model always points to the default block model.
        # For mirrored blocks, the default is assumed to be type "single" with no extra variant suffix.
        item_model_data = {
            "parent": f"furnies:block/{block_type}/{name}"
        }
        item_model_filename = os.path.join(item_models_dir, f"{name}_{block_type}.json")
        with open(item_model_filename, 'w') as f:
            json.dump(item_model_data, f, indent=4)
        print(f"Generated item model: {item_model_filename}")

if __name__ == "__main__":
    main()
